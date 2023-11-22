package be.vamaralds.merode.model

import arrow.core.*
import be.vamaralds.merode.instance.*
import be.vamaralds.merode.instance.AttributeValue.Companion.attributeValue
import be.vamaralds.merode.instance.Property.Companion.property

data class EventType(val name: String, val ownerType: BusinessObjectType, val attributes: Set<Attribute> = ownerType.attributes) {
    private val attributesByName: Map<String, Attribute> by lazy {
        attributes.associateBy {
            it.name
        }}

    fun attribute(name: String): Either<AttributeNotFoundError, Attribute> =
        attributesByName[name]?.right() ?: AttributeNotFoundError(name, this.name).left()

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