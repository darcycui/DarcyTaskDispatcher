package task.withreturn

import dispatcher.TDispatchers
import entity.TaskResult

interface IWithReturnTask<T> {
    fun getTaskName(): String
    fun getDispatcher(): TDispatchers
    suspend fun onRun(): TaskResult<T>
}