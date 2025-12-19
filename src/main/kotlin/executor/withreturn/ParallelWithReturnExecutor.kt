package executor.withreturn

import kotlinx.coroutines.*
import entity.TaskResult
import task.withreturn.IWithReturnTask

class ParallelWithReturnExecutor private constructor() : IWithReturnExecutor<String> {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override suspend fun execute(tasks: List<IWithReturnTask<String>>): List<TaskResult<String>> {
        return coroutineScope {
            val deferredList: MutableList<Deferred<TaskResult<String>>> = mutableListOf()
            tasks.forEach { task ->
                deferredList.add(
                    async(scope.coroutineContext) {
                        println("${task.getTaskName()} 在线程: ${Thread.currentThread().name} 中执行...")
                        task.onRun()
                    })
            }
            deferredList.awaitAll()
        }
    }

    companion object {
        private var instance: ParallelWithReturnExecutor? = null
        fun getInstance(): ParallelWithReturnExecutor {
            if (instance == null) {
                instance = ParallelWithReturnExecutor()
            }
            return instance!!
        }
    }
}