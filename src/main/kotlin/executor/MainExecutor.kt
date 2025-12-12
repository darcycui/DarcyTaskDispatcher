package org.example.executor

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import task.IJob

class MainExecutor private constructor() : IExecutor {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val taskChannel = Channel<IJob>(Channel.UNLIMITED)

    // 模拟主线程
    private val mainDispatcher = newSingleThreadContext("main")

    companion object {
        private var instance: MainExecutor? = null
        fun getInstance(): MainExecutor {
            return instance ?: MainExecutor().also {
                instance = it
            }
        }
    }

    init {
        startConsumer()
    }

    private fun startConsumer() {
        scope.launch(mainDispatcher) {
            // 消费者：从队列中取出任务并执行
            while (isActive) {
                val task = taskChannel.receive()
                println("当前线程: ${Thread.currentThread().name}")
                task.onRun()
            }
        }
    }

    override fun execute(jobs: List<IJob>) {
        scope.launch(mainDispatcher) {
            // 生产者：将任务放入队列
            for (job in jobs) {
                taskChannel.send(job)
            }
        }
    }
}