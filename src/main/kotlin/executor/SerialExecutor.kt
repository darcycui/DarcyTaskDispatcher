package org.example.executor

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.example.entity.JobResult
import task.IJob

class SerialExecutor private constructor(): IExecutor {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val jobChannel = Channel<IJob>(Channel.UNLIMITED)
    companion object {
        private var instance: SerialExecutor? = null
        fun getInstance(): SerialExecutor {
            return instance ?: SerialExecutor().also {
                instance = it
            }
        }
    }
    init {
        // 启动消费者协程
        startConsumer()
    }
    private fun startConsumer() {
        scope.launch() {
            // 消费者：从队列中取出任务并执行
            for (job in jobChannel) {
                println("${job.getJobName()} 在线程: ${Thread.currentThread().name} 中执行...")
                job.onRun()
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