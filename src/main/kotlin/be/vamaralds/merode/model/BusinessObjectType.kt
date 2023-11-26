package be.vamaralds.merode.model

import arrow.core.*
import arrow.core.raise.either
import be.vamaralds.merode.instance.*

/**
 * Represents a [BusinessObjectType] in the Merode [Model].
 * @param name The name of the [BusinessObjectType].
 * @param stateMachine The [StateMachine] defining the behavior of the [BusinessObjectType] instances ([BusinessObject]s).
 * @param attributes The user-defined [Attribute]s of the [BusinessObjectType], representing its data structure
 */
data class BusinessObjectType(val name: String, val stateMachine: StateMachine? = null, val attributes: Set<Attribute> = emptySet()) {
    /**
     * A map of the [attributes] of this [BusinessObjectType] by their [Attribute.name].
     */
    private val attributesByName: Map<String, Attribute> by lazy {
        attributes.associateBy {
            it.name
        }}

    /**
     * Creates a new [BusinessObjectType] with the provided [name] and [attributes] (empty by default). The created [BusinessObjectType] will not have a [StateMachine]. It must be set using the [stateMachine] method.
     */
    constructor(name: String, attributes: Set<Attribute> = emptySet()): this(name, null, attributes)

    /**
     * @return a copy of this [BusinessObjectType], to which [stateMachine] is used to define the object behavior.
     */
    fun stateMachine(stateMachine: StateMachine): BusinessObjectType =
        this.copy(stateMachine = stateMachine)

    /**
     * @return The [Attribute] named [name] of this [BusinessObjectType], if it exists
     * @return An [AttributeNotFoundError] if the [Attribute] does not exist in this [BusinessObjectType]
     */
    fun attribute(name: String): Either<AttributeNotFoundError, Attribute> =
        attributesByName[name]?.right() ?: AttributeNotFoundError(name, this.name).left()

    /**
     * This method enables the creation of instances of this [BusinessObjectType]. It should be used to create new [BusinessObject]s of this [BusinessObjectType] to which no [Event] has been applied yet.
     * @return If the provided property values are valid, returns a new [BusinessObject] of this [BusinessObjectType] with the provided property values ([propsValues]). [Attribute]s without a value will be set to null. The [BusinessObject.id] is set to -1, and the [BusinessObject.state] is set to [State.Initial].
     * @return A [NonEmptyList] of [InstanceError]s if one of the provided property values in invalid.
     */
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

    override fun toString(): String {
        val sb = StringBuilder()
        sb.appendLine(this.name)
        sb.appendLine("  Attributes:")
        this.attributes.forEach { attr ->
            sb.appendLine("    ${attr.name}: ${attr.type.name}")
        }
        sb.appendLine("  State Machine Transitions:")
        sb.appendLine(this.stateMachine)

        return sb.toString()
    }
}