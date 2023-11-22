package be.vamaralds.merode.model

import arrow.core.*
import arrow.core.raise.either
import be.vamaralds.merode.instance.*

data class BusinessObjectType(val name: String, val stateMachine: StateMachine? = null, val attributes: Set<Attribute> = emptySet()) {
    constructor(name: String, attributes: Set<Attribute> = emptySet()): this(name, null, attributes)

    fun stateMachine(stateMachine: StateMachine): BusinessObjectType =
        this.copy(stateMachine = stateMachine)

    private val attributesByName: Map<String, Attribute> by lazy {
        attributes.associateBy {
        it.name
    }}

    fun attribute(name: String): Either<AttributeNotFoundError, Attribute> =
        attributesByName[name]?.right() ?: AttributeNotFoundError(name, this.name).left()

    operator fun invoke(): EitherNel<InstanceError, BusinessObject> =
        this(-1, State.Initial)

    operator fun invoke(id: Long, state: State, vararg propsValues: Any?): EitherNel<InstanceError, BusinessObject> = either {
        val attributes = this@BusinessObjectType.attributes
        val values = propsValues.toList()
        if(attributes.size != values.size)
            raise(MissingPropertyError("The number of properties (${values.size}) does not match the number of attributes for type ${this@BusinessObjectType.name} (${attributes.size})").nel())
        val attributesProperties = attributes.zip(values)

        val props = attributesProperties.mapOrAccumulate {
            val (attribute, value) = it
            Property.property(attribute, value).bind()
        }.bind().toSet()

        BusinessObject(this@BusinessObjectType, id, state, props)
    }

    operator fun invoke(id: Long, state: State = State.Initial, props: Map<String, Any?> = emptyMap()): EitherNel<InstanceError, BusinessObject> {
        val errors: MutableList<InstanceError> = mutableListOf()
        val properties: MutableSet<Property> = mutableSetOf()
        attributes.forEach { attribute ->
            val property = Property.property(attribute, null)
            property.map(properties::add)
                .mapLeft(errors::add)
        }

        props.forEach { (name, value) ->
            val attribute = attributesByName[name]
            if(attribute == null) {
                errors.add(PropertyNotFoundError(name, this.name))
            } else {
                val prop = Property.property(attribute, value)
                prop.map {
                    properties.removeIf { prop -> prop.attribute.name == name }
                    properties.add(it)
                }.mapLeft(errors::add)
            }
        }

        return if(errors.isNotEmpty())
            errors.toNonEmptyListOrNull()!!.left()
        else
            BusinessObject(this, id, state, properties).right()
    }
}