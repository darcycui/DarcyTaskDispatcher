package org.example.dispatcher

sealed class TDispatchers {
    data object Main : TDispatchers()
    data object Parallel : TDispatchers()
    data object Serial : TDispatchers()

    override fun toString(): String {
        return super.toString()
    }
}