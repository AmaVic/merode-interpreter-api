package be.vamaralds.merode.instance

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.mapOrAccumulate
import arrow.core.raise.zipOrAccumulate
import arrow.fx.coroutines.parMapOrAccumulate
import be.vamaralds.merode.instance.Property.Companion.property
import be.vamaralds.merode.model.Attribute
import be.vamaralds.merode.model.BusinessObjectType
import be.vamaralds.merode.model.State
import java.lang.ClassCastException

data class BusinessObject(
    val type: BusinessObjectType,
    val id: Long,
    val state: State,
    private val properties: Set<Property> = emptySet(),
) {
    private val propertiesByName: Map<String, Property> by lazy { properties.associateBy { it.attribute.name } }

    operator fun<T> get(propertyName: String): Either<InstanceError, T?> = either {
        val property = propertiesByName[propertyName] ?: raise(PropertyNotFoundError(propertyName, type.name))

        val typedValue = try {
            property.value.value?.let { it as T }
        } catch (e: ClassCastException) {
            raise(PropertyTypeError(propertyName, type.name, property.value.value!!::class.simpleName ?: "Unknown"))
        }

        typedValue
    }

    fun handleEvent(event: Event): EitherNel<EventHandlingError, BusinessObject> = either {
        val currentProperties = this@BusinessObject.properties.associateBy { it.attribute.name }
        val eventProperties = event.properties.associateBy { it.attribute.name }
        val propertiesNames = currentProperties.keys
        val propertiesValues = propertiesNames.map { propName ->
            val oldValue = currentProperties[propName]!!.value
            val eventValue = eventProperties[propName]?.value
            eventValue ?: oldValue
        }

        val resultingPropertiesOp = propertiesNames.zip(propertiesValues)
        val resultingProperties = resultingPropertiesOp.mapOrAccumulate {
            val (propName, propValue) = it
            val attribute = type.attribute(propName)
                .mapLeft { EventHandlingError(it.toString()) }
                .bind()
            property(attribute, propValue.value).bind()
        }.mapLeft {
            it.map { EventHandlingError(it.toString()) }
        }

        val nextStateOp: Either<EventHandlingError, State> =
            type.stateMachine?.nextState(state, event.type)
                ?: EventHandlingError("No state machine defined for type ${type.name}").left()

        zipOrAccumulate(
            { resultingProperties.bind().toSet() },
            { nextStateOp.bind() }
        ) { p, s ->
            this@BusinessObject.copy(id = event.objectId, properties = p, state = s)
        }
    }

    override fun toString(): String {
        val idPropStr = "id: $id"
        val statePropStr = "state: ${state.name}"
        val idAndStateAndProps = setOf(idPropStr, statePropStr) +
                properties.map { "${it.attribute.name}: ${it.value}" }
                    .joinToString(", ")
        val builder = StringBuilder("${type.name} $idAndStateAndProps")
        return builder.toString()
    }
}