package org.example.manager

import org.example.dispatcher.TDispatchers
import org.example.executor.DefaultExecutor
import org.example.executor.IExecutor
import org.example.executor.MainExecutor
import task.IJob

class TaskManager {
    companion object {
        private var instance: TaskManager? = null
        fun getInstance(): TaskManager {
            if (instance == null) {
                instance = TaskManager()
            }
            return instance!!
        }
    }

    fun addJob(job: IJob) {
        val dispatcher: TDispatchers = job.getDispatcher()
        println("添加任务: ${job.getJobName()} 调度器:$dispatcher -->${System.identityHashCode(this)}")
        when (job.getDispatcher()) {
            TDispatchers.Main -> {
                val executor: IExecutor = MainExecutor.getInstance()
                executor.execute(job)
            }

            TDispatchers.IO -> {
            }

            TDispatchers.Cpu -> {

            }

            TDispatchers.Default -> {
                val executor: IExecutor = DefaultExecutor.getInstance()
                executor.execute(job)
            }
        }
    }
}