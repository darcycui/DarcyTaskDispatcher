package executor.noreturn

import task.noreturn.INoReturnValueTask

/**
 * 执行器 执行没有返回值的任务
 */
interface INoReturnExecutor {
    fun execute(tasks: List<INoReturnValueTask>)
}