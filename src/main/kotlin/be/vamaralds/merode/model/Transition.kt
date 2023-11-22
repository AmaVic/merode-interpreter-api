package be.vamaralds.merode.model

import be.vamaralds.merode.instance.Event

data class Transition(val onEventType: EventType, val fromState: State, val toState: State)