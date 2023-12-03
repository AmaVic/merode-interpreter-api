package be.vamaralds.merode.model

fun testModel(): Model {
    val objectType = customerType()
    val model = Model(setOf(objectType), emptySet())
    return model
}