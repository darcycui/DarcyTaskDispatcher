package task

import org.example.dispatcher.TDispatchers

interface IJob {
    fun getJobName(): String
    fun getDispatcher(): TDispatchers
    suspend fun onRun()
}