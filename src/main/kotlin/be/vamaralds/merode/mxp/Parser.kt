package be.vamaralds.merode.mxp

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import be.vamaralds.merode.config.Config
import be.vamaralds.merode.model.*
import com.sun.xml.bind.v2.schemagen.xmlschema.AttributeType
import org.xml.sax.SAXException
import java.math.BigInteger
import java.nio.file.Path
import javax.xml.XMLConstants
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Unmarshaller
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.Schema
import javax.xml.validation.SchemaFactory


class Parser(private val mxpFilePath: Path) {
    private val schemaStream = this::class.java.classLoader.getResourceAsStream(Config.MXP_SCHEMA_PATH)
    private var metamodel: Metamodel? = null
    private val objectTypes: MutableMap<BigInteger, BusinessObjectType> = mutableMapOf()
    private val eventTypes: MutableMap<BigInteger, EventType> = mutableMapOf()

    init {
        parseMxp().map {
            metamodel = it
        }.mapLeft {
            throw it
        }
    }

    fun parseModel(): Either<ModelParsingError, Model> = either {
        //Register BOTs without FSMs
        metamodel!!.metaobjects.metaobject.forEach(::parseBusinessObjectTypeWithoutFSM)

        val businessObjectTypes = metamodel!!.metaobjects.metaobject.map { parseBusinessObjectType(it) }.toSet()

        Model(businessObjectTypes)
    }

    private fun parseMxp(): Either<ModelParsingError, Metamodel> = either {
        val jaxbContext: JAXBContext = try {
            JAXBContext.newInstance(Mermaidmodel::class.java)
        } catch(e: JAXBException) {
            raise(ModelParsingError("Failed to create JAXB context: ${e.message}"))
        }

        val factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        val schema: Schema = try {
            factory.newSchema(StreamSource(schemaStream))
        } catch (e: SAXException) {
            raise(ModelParsingError("Could not Create Schema Factory (${e})"))
        }

        val jaxbUnmarshaller: Unmarshaller = try {
            jaxbContext.createUnmarshaller()
        } catch (e: JAXBException) {
            raise(ModelParsingError("Could not Create Unmarshaller"))
        }

        jaxbUnmarshaller.schema = schema

        val mermaidmodel: Mermaidmodel = try {
            jaxbUnmarshaller.unmarshal(mxpFilePath.toFile()) as Mermaidmodel
        } catch (e: JAXBException) {
            raise(ModelParsingError("Could not Unmarshal File: $e"))
        }

        mermaidmodel.metamodel
    }

    private fun parseBusinessObjectTypeWithoutFSM(metaobject: Metaobject): BusinessObjectType {
        if(objectTypes.containsKey(metaobject.id)) {
            return objectTypes[metaobject.id]!!
        }

        val typeName = metaobject.name

        val metaAttributes = metaobject.metaattributes.metaattribute
        val attributes = metaAttributes.map(::parseAttribute).toSet()

        val type = BusinessObjectType(typeName, attributes)
        objectTypes[metaobject.id] = type

        return type
    }

    private fun parseBusinessObjectType(metaobject: Metaobject): BusinessObjectType {
        val typeWithoutFSM = parseBusinessObjectTypeWithoutFSM(metaobject)

        val metaFsm = MxpHelper.getMetaFSM(metaobject)
        val stateMachine = parseStateMachine(metaFsm)

        val typeWithFsm = typeWithoutFSM.copy(stateMachine = stateMachine)
        objectTypes[metaobject.id] = typeWithFsm

        return typeWithFsm
    }

    private fun parseAttribute(metaAttribute: Metaattribute): Attribute {
        val name = metaAttribute.name
        val type = when(metaAttribute.type) {
            "String" -> Attribute.Type.String
            "Integer" -> Attribute.Type.Int
            "Float" -> Attribute.Type.Float
            "Double" -> Attribute.Type.Double
            "Boolean" -> Attribute.Type.Boolean
            else -> throw ModelParsingError("Unknown Attribute Type: ${metaAttribute.type}")
        }

        return Attribute(name, type)
    }

    private fun parseStateMachine(metaFsm: Metafsm): StateMachine {
        val statesById = MxpHelper.getStatesById(metaFsm)
        val transitions = metaFsm.metatransitions.metatransition.map { parseTransitions(statesById, it) }.flatten()
        return StateMachine(transitions.toSet())
    }

    private fun parseTransitions(statesById: Map<Long, State>, metaTransition: Metatransition): List<Transition> {
        val sourceStateId = metaTransition.sourcestateid.toLong()
        val sourceState = statesById[sourceStateId]!!

        val targetStateId = metaTransition.targetstateid.toLong()
        val targetState = statesById[targetStateId]!!

        val metaTransitionMethods = metaTransition.metatransitionmethods.metatransitionmethod
        return metaTransitionMethods.map { transitionMethod ->
            val methods = metamodel!!.metamethods.metamethod.filter {
                it.id == transitionMethod.methodid
            }

            methods.map { method ->
                val triggeringEventId = method.ownereventid
                val triggeringMetaEvent = metamodel!!.metaevents.metaevent.find { it.id == triggeringEventId }

                val ownedMethod = metamodel!!.metamethods.metamethod.find { it.name == method.name && it.provenance == "OWNED" }!!
                val ownerMetaobjectId = ownedMethod.ownerobjectid!!
                val ownerBusinessObjectType = objectTypes[ownerMetaobjectId]!!

                val ownerEffect = when(ownedMethod.type) {
                    "CREATE" -> EventType.OwnedEffect.Create
                    "MODIFY" -> EventType.OwnedEffect.Modify
                    "END" -> EventType.OwnedEffect.End
                    else -> throw ModelParsingError("Unknown Owned Effect Type: ${ownedMethod.type}")
                }

                val eventTypeName = triggeringMetaEvent!!.name
                val eventTypeOwner = ownerBusinessObjectType
                val eventTypeOwnerEffect = ownerEffect

                val eventType = EventType(eventTypeName, eventTypeOwner, eventTypeOwnerEffect)
                eventTypes[triggeringEventId] = eventType

                Transition(eventType, sourceState, targetState)
            }
        }.flatten()
    }

}