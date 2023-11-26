package be.vamaralds.merode.model

import be.vamaralds.merode.common.MerodeError

/**
 * Represents errors in the [Model] domain, not in its instantiation.
 */
open class ModelError(msg: String? = null): MerodeError(msg)

/**
 * This error occurs when trying to access an [Attribute] that does not exist in a [BusinessObjectType].
 */
class AttributeNotFoundError(attributeName: String, businessObjectTypeName: String): ModelError("Attribute '$attributeName' not found in Business Object Type '$businessObjectTypeName'")

class ModelParsingError(msg: String? = null): ModelError(msg)