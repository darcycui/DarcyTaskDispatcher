package org.example.executor

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.example.entity.JobResult
import task.IJob

class ParallelExecutor : IExecutor {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val jobChannel = Channel<IJob>(Channel.UNLIMITED)

    companion object {
        private var instance: ParallelExecutor? = null
        fun getInstance(): ParallelExecutor {
            if (instance == null) {
                instance = ParallelExecutor()
            }
            return instance!!
        }
    }

    init {
        startConsumer()
    }

    private fun startConsumer() {
        repeat(5) {
            scope.launch {
                while (isActive) {
                    val job = jobChannel.receive()
                    println("${job.getJobName()} 在线程: ${Thread.currentThread().name} 中执行...")
                    job.onRun()
                }
            }
        }
    }

    override fun execute(jobs: List<IJob>) {
        scope.launch() {
            // 生产者：将任务放入队列
            for (job in jobs) {
                jobChannel.send(job)
            }
        }
    }
}