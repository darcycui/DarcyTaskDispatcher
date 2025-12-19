package executor.noreturn

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import task.noreturn.INoReturnValueTask

class SerialNoReturnExecutor private constructor(): INoReturnExecutor {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val taskChannel = Channel<INoReturnValueTask>(Channel.UNLIMITED)
    companion object {
        private var instance: SerialNoReturnExecutor? = null
        fun getInstance(): SerialNoReturnExecutor {
            return instance
                    ?: SerialNoReturnExecutor().also {
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
            for (task in taskChannel) {
                println("${task.getTaskName()} 在线程: ${Thread.currentThread().name} 中执行...")
                task.onRun()
            }
        }
    }

    override fun execute(tasks: List<INoReturnValueTask>) {
        scope.launch() {
            // 生产者：将任务放入队列
            for (task in tasks) {
                taskChannel.send(task)
            }
        }
    }
}