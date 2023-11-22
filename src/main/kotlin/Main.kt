import arrow.core.Nel
import arrow.core.raise.either
import be.vamaralds.merode.common.MerodeError
import be.vamaralds.merode.model.*

fun main(args: Array<String>) {
    //Business Object Types
    var Customer = BusinessObjectType("Customer", setOf(
        Attribute("name", Attribute.Type.String),
        Attribute("banReason", Attribute.Type.String),
    ))

    //Event Types
    val CreateCustomerEvent = EventType("CreateCustomer", Customer)
    val UpdateCustomerNameEvent = EventType("UpdateCustomerName", Customer, setOf(
        Attribute("name", Attribute.Type.String)
    ))
    val BanCustomerEvent = EventType("BanCustomer", Customer, setOf(
        Attribute("banReason", Attribute.Type.String)
    ))

    //States
    val InitialCustomerState = State.Initial
    val CreatedCustomerState = State("Created")

    //StateMachines
    val customerFSM = StateMachine(setOf(
        Transition(CreateCustomerEvent, InitialCustomerState, CreatedCustomerState),
        Transition(UpdateCustomerNameEvent, CreatedCustomerState, CreatedCustomerState),
        Transition(BanCustomerEvent, CreatedCustomerState, CreatedCustomerState)
    ))

    Customer = Customer.stateMachine(customerFSM)

    //Model
    val model = Model(
        "Customer",
        setOf(Customer),
        setOf(CreateCustomerEvent, UpdateCustomerNameEvent, BanCustomerEvent),
    )

    println(model)

    //Instances
    either<Nel<MerodeError>, Any> {
        val evt = CreateCustomerEvent(0, 0, mapOf("name" to "George")).bind()
        println(evt)

        var cst = Customer().bind()
        println(cst)

        cst = cst.handleEvent(evt).bind()
        println(cst)

        val updateNameEvent = UpdateCustomerNameEvent(1, 0, mapOf("name" to "John")).bind()
        println(updateNameEvent)

        cst = cst.handleEvent(updateNameEvent).bind()
        println(cst)

        val banCustomerEvent = BanCustomerEvent(2, 0, mapOf("banReason" to "Fraud")).bind()
        println(banCustomerEvent)

        cst = cst.handleEvent(banCustomerEvent).bind()
        println(cst)

    }.mapLeft {
        it.forEach { it.printStackTrace() }
    }
}