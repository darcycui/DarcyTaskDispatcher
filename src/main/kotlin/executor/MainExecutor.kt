package org.example.executor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import task.IJob

class MainExecutor private constructor(): IExecutor {
    private val scope: CoroutineScope = GlobalScope
    companion object {
        private var instance: MainExecutor? = null
        fun getInstance(): MainExecutor {
            return instance ?: MainExecutor().also {
                instance = it
            }
        }
    }
    override fun execute(job: IJob) {
        scope.launch(Dispatchers.Main) {
            println("当前线程: ${Thread.currentThread().name}")
            job.onRun()
        }
    }
}