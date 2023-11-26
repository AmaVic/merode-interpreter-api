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

    val CreateCustomerEvent = EventType("CreateCustomer", Customer, EventType.OwnedEffect.Create)
    val UpdateCustomerNameEvent = EventType(
        "UpdateCustomerName", Customer, EventType.OwnedEffect.Modify, setOf(
            Attribute("name", Attribute.Type.String)
        )
    )
    val BanCustomerEvent = EventType(
        "BanCustomer", Customer, EventType.OwnedEffect.End, setOf(
            Attribute("banReason", Attribute.Type.String)
        )
    )

    val InitialCustomerState = State.Initial
    val CreatedCustomerState = State("Created", State.Type.Living)
    val BannedCustomerState = State("Banned", State.Type.Final)

    val customerFSM = StateMachine(
        setOf(
            Transition(CreateCustomerEvent, InitialCustomerState, CreatedCustomerState),
            Transition(UpdateCustomerNameEvent, CreatedCustomerState, CreatedCustomerState),
            Transition(BanCustomerEvent, CreatedCustomerState, BannedCustomerState)
        )
    )

    return Customer.stateMachine(customerFSM)
}
