package be.vamaralds.merode.model

data class ExistenceDependency(
    val name: String,
    val master: BusinessObjectType,
    val dependent: BusinessObjectType,
    val type: Type
) {
    enum class Type {
        OptionalOne,
        MandatoryOne,
        OptionalMany,
        MandatoryMany
    }
}