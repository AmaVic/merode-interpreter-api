package be.vamaralds.merode.model

fun testObjectType(): BusinessObjectType {

    val Customer = BusinessObjectType(
        "Customer", setOf(
            Attribute("name", Attribute.Type.String),
            Attribute("isPremium", Attribute.Type.Boolean),
            Attribute("discountPct", Attribute.Type.Double),
            Attribute("deliveryDiscountPc", Attribute.Type.Float),
            Attribute("age", Attribute.Type.Int),
            Attribute("banReason", Attribute.Type.String),
        )
    )

    val CreateCustomerEvent = EventType("CreateCustomer", Customer)
    val UpdateCustomerNameEvent = EventType(
        "UpdateCustomerName", Customer, setOf(
            Attribute("name", Attribute.Type.String)
        )
    )
    val BanCustomerEvent = EventType(
        "BanCustomer", Customer, setOf(
            Attribute("banReason", Attribute.Type.String)
        )
    )

    val InitialCustomerState = State.Initial
    val CreatedCustomerState = State("Created")

    val customerFSM = StateMachine(
        setOf(
            Transition(CreateCustomerEvent, InitialCustomerState, CreatedCustomerState),
            Transition(UpdateCustomerNameEvent, CreatedCustomerState, CreatedCustomerState),
            Transition(BanCustomerEvent, CreatedCustomerState, CreatedCustomerState)
        )
    )

    return Customer.stateMachine(customerFSM)
}
