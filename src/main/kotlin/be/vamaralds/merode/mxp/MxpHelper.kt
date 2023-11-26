package be.vamaralds.merode.mxp

import be.vamaralds.merode.model.ModelParsingError
import be.vamaralds.merode.model.State

object MxpHelper {
    fun getMetaFSM(metaobject: Metaobject): Metafsm =
        metaobject.metafsms.metafsm.find { it.codegeneration }!!

    fun getStatesById(metaFsm: Metafsm): Map<Long, State> =
        metaFsm.metastates.metastate.map { metaState ->
            val id = metaState.id.toLong()
            val name = metaState.name
            val type = when(metaState.type) {
                "INITIAL_STATE" -> State.Type.Initial
                "STATE" -> State.Type.Living
                "FINAL_STATE" -> State.Type.Final
                else -> throw ModelParsingError("Unknown State Type: ${metaState.type}")
            }

            id to State(name, type)
        }.toMap()
}