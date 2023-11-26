package be.vamaralds.merode.model

fun testModel(): Model {
    val objectType = testObjectType()
    val model = Model(setOf(objectType))
    return model
}