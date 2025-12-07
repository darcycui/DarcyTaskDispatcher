package org.example.dispatcher

sealed class TDispatchers {
    data object Main : TDispatchers()
    data object IO : TDispatchers()
    data object Cpu : TDispatchers()
    data object Default : TDispatchers()

    override fun toString(): String {
        return super.toString()
    }
}