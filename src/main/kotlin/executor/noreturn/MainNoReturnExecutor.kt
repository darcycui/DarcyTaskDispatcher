package executor.noreturn

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import task.noreturn.INoReturnValueTask

class MainNoReturnExecutor private constructor() : INoReturnExecutor {
    // 模拟主线程
    private val mainDispatcher = newSingleThreadContext("main")
    private val scope: CoroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())
    private val taskChannel = Channel<INoReturnValueTask>(Channel.UNLIMITED)


    companion object {
        private var instance: MainNoReturnExecutor? = null
        fun getInstance(): MainNoReturnExecutor {
            return instance
                    ?: MainNoReturnExecutor().also {
                instance = it
            }
        }
    }

    init {
        startConsumer()
    }

    private fun startConsumer() {
        scope.launch(scope.coroutineContext) {
            // 消费者：从队列中取出任务并执行
            while (isActive) {
                val task = taskChannel.receive()
                println("当前线程: ${Thread.currentThread().name}")
                task.onRun()
            }
        }
    }

    override fun execute(tasks: List<INoReturnValueTask>) {
        scope.launch(mainDispatcher) {
            // 生产者：将任务放入队列
            for (task in tasks) {
                taskChannel.send(task)
            }
        }
    }
}