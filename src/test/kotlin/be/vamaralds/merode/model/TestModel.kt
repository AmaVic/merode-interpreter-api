package be.vamaralds.merode.model

fun testModel(): Model {
    val objectType = testObjectType()
    val model = Model("Store", setOf(objectType))
    return model
}