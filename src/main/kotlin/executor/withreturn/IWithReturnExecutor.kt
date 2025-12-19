package executor.withreturn

import entity.TaskResult
import task.withreturn.IWithReturnTask

/**
 * 一次性执行器
 * 执行完任务后立即退出
 */
interface IWithReturnExecutor<T> {
    suspend fun execute(tasks: List<IWithReturnTask<T>>): List<TaskResult<T>>
}