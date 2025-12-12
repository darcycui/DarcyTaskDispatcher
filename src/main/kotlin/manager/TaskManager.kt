package org.example.manager

import org.example.dispatcher.TDispatchers
import org.example.executor.SerialExecutor
import org.example.executor.IExecutor
import org.example.executor.MainExecutor
import org.example.executor.ParallelExecutor
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

    fun addJob(jobs: List<IJob>) {
        if (jobs.isEmpty()) {
            println("任务列表为空, 结束.")
            return
        }
        // 判断jobs的调度器是否一致 不一致的话抛出异常
        val dispatchers = jobs.map { it.getDispatcher() }.distinct()
        if (dispatchers.size > 1) {
            throw IllegalArgumentException("所有任务调度器必须一致, 当前发现调度器: ${dispatchers.joinToString()}")
        }
        // 获取调度器
        val dispatcher: TDispatchers = jobs[0].getDispatcher()
        jobs.forEach { job ->
            println("添加任务: ${job.getJobName()} 调度器:$dispatcher -->${System.identityHashCode(this)}")
        }
        when (dispatcher) {
            TDispatchers.Main -> {
                val executor: IExecutor = MainExecutor.getInstance()
                executor.execute(jobs)
            }

            TDispatchers.Serial -> {
                val executor: IExecutor = SerialExecutor.getInstance()
                executor.execute(jobs)
            }

            TDispatchers.Parallel -> {
                val executor: IExecutor = ParallelExecutor.getInstance()
                executor.execute(jobs)
            }
        }
    }
}