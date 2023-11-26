package be.vamaralds.merode.model

import arrow.core.*
import be.vamaralds.merode.instance.*
import be.vamaralds.merode.instance.AttributeValue.Companion.attributeValue
import be.vamaralds.merode.instance.Property.Companion.property

/**
 * Represents an [EventType] in a Merode [Model]. These types can be instantiated as [Event]s.
 * The [Event]s can be applied to [BusinessObject]s of the [EventType.ownerType] using the [BusinessObject.handleEvent] method.
 * @param name The name of the [EventType].
 * @param ownerType The [BusinessObjectType] that participates in the [EventType] with an owned method.
 * @param attributes The [Attribute]s of the [EventType]. These are the properties of the [Event]s that can be created from this [EventType].
 * @param ownerEffect The effect of the [EventType] on the [ownerType]. This can be [OwnedEffect.Create], [OwnedEffect.Modify] or [OwnedEffect.End].
 */
data class EventType(val name: String, var ownerType: BusinessObjectType, val ownerEffect: OwnedEffect, val attributes: Set<Attribute> = ownerType.attributes) {
    enum class OwnedEffect {
        Create,
        Modify,
        End
    }

    /**
     * A map of the [attributes] of this [EventType] by their [Attribute.name].
     */
    private val attributesByName: Map<String, Attribute> by lazy {
        attributes.associateBy {
            it.name
        }}

    /**
     * @return The [Attribute] named [name] of this [EventType], if it exists
     * @return An [AttributeNotFoundError] if the [Attribute] does not exist in this [EventType]
     */
    fun attribute(name: String): Either<AttributeNotFoundError, Attribute> =
        attributesByName[name]?.right() ?: AttributeNotFoundError(name, this.name).left()

    /**
     * This method enables creating instances of this [EventType] (called [Event]s).
     * @param eventId The id of the [Event] to create. It must be unique in a given set of [Event]s.
     * @param objectId The id of the [BusinessObject] targeted by the [Event].
     * @param props A map of property names and values to set on the [Event].
     * @return If the provided property values are valid, returns a new [Event] of this [EventType] with the provided property values ([props]).
     * @return A [NonEmptyList] of [InstanceError]s if one of the provided property values is invalid.
     */
    operator fun invoke(eventId: Long, objectId: Long, props: Map<String, Any?> = emptyMap()): EitherNel<InstanceError, Event> {
        val errors: MutableList<InstanceError> = mutableListOf()
        val properties: MutableSet<Property> = mutableSetOf()
        attributes.forEach { attribute ->
            val property = property(attribute, null)
            property.map(properties::add)
                .mapLeft(errors::add)
        }

        props.forEach { (name, value) ->
            val attribute = attributesByName[name]
            if(attribute == null) {
                errors.add(PropertyNotFoundError(name, this.name))
            } else {
                attributeValue(value).map { propVal ->
                    val prop = Property.property(attribute, propVal.value)
                    prop.map {
                        properties.removeIf { prop -> prop.attribute.name == name }
                        properties.add(it)
                    }.mapLeft(errors::add)
                }
            }
        }

        return if(errors.isNotEmpty())
            errors.toNonEmptyListOrNull()!!.left()
        else
            Event(this, eventId, objectId, properties).right()
    }
}