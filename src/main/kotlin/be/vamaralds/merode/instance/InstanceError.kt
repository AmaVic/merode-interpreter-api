package be.vamaralds.merode.instance

import be.vamaralds.merode.common.MerodeError

open class InstanceError(msg: String? = null): MerodeError(msg)
class PropertyNotFoundError(attributeName: String, businessObjectTypeName: String): InstanceError("Property '$attributeName' not found in Type '$businessObjectTypeName'")
class PropertyTypeError(propertyName: String, expectedType: String, actualType: String): InstanceError("Property '$propertyName' has type '$actualType' but should have type '$expectedType'")
class MissingPropertyError(msg: String? = null): InstanceError(msg) {
    constructor(propertyName: String, businessObjectTypeName: String): this("Property '$propertyName' is missing for Type '$businessObjectTypeName'")
}
class ValueTypeError(msg: String? = null): InstanceError(msg)
class EventHandlingError(msg: String? = null): InstanceError(msg)