package be.vamaralds.merode.model

import be.vamaralds.merode.common.MerodeError

open class ModelError(msg: String? = null): MerodeError(msg)
class AttributeNotFoundError(attributeName: String, businessObjectTypeName: String): ModelError("Attribute '$attributeName' not found in Business Object Type '$businessObjectTypeName'")