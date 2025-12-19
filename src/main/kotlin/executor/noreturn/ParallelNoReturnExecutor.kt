package executor.noreturn

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import task.noreturn.INoReturnValueTask

class ParallelNoReturnExecutor : INoReturnExecutor {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val taskChannel = Channel<INoReturnValueTask>(Channel.UNLIMITED)

    companion object {
        private var instance: ParallelNoReturnExecutor? = null
        fun getInstance(): ParallelNoReturnExecutor {
            if (instance == null) {
                instance = ParallelNoReturnExecutor()
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
                    val task = taskChannel.receive()
                    println("${task.getTaskName()} 在线程: ${Thread.currentThread().name} 中执行...")
                    task.onRun()
                }
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