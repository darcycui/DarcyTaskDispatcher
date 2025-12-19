package executor.withreturn

import entity.TaskResult
import kotlinx.coroutines.*
import task.withreturn.IWithReturnTask

class MainWithReturnExecutor : IWithReturnExecutor<String> {
    // 模拟主线程
    private val mainDispatcher = newSingleThreadContext("main")

    private val scope: CoroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())
    companion object {
        private var instance: MainWithReturnExecutor? = null
        fun getInstance(): MainWithReturnExecutor {
            if (instance == null) {
                instance = MainWithReturnExecutor()
            }
            return instance!!
        }
    }
    override suspend fun execute(tasks: List<IWithReturnTask<String>>): List<TaskResult<String>> {
        return withContext(scope.coroutineContext) {
            val resultList: MutableList<TaskResult<String>> = mutableListOf()
            for (task in tasks) {
                println("${task.getTaskName()} 在线程: ${Thread.currentThread().name} 中执行...")
                resultList.add(task.onRun())
            }
            resultList
        }
    }
}