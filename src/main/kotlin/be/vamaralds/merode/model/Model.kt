package be.vamaralds.merode.model

import be.vamaralds.merode.instance.Event

data class Model(
    val name: String,
    val objectTypes: Set<BusinessObjectType>,
    val eventTypes: Set<EventType>,
)