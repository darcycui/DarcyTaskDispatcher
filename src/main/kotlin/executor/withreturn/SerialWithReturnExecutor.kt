package executor.withreturn

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import entity.TaskResult
import kotlinx.coroutines.withContext
import task.withreturn.IWithReturnTask

class SerialWithReturnExecutor : IWithReturnExecutor<String> {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    companion object {
        private var instance: SerialWithReturnExecutor? = null
        fun getInstance(): SerialWithReturnExecutor {
            if (instance == null) {
                instance = SerialWithReturnExecutor()
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