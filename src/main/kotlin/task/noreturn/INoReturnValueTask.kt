package task.noreturn

import dispatcher.TDispatchers

interface INoReturnValueTask {
    fun getTaskName(): String
    fun getDispatcher(): TDispatchers
    suspend fun onRun(): Unit
}