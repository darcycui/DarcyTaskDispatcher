package manager

import dispatcher.TDispatchers
import executor.noreturn.SerialNoReturnExecutor
import executor.noreturn.INoReturnExecutor
import executor.noreturn.MainNoReturnExecutor
import executor.noreturn.ParallelNoReturnExecutor
import executor.withreturn.ParallelWithReturnExecutor
import entity.TaskResult
import executor.withreturn.MainWithReturnExecutor
import executor.withreturn.SerialWithReturnExecutor
import task.noreturn.INoReturnValueTask
import task.withreturn.IWithReturnTask

class TaskManager {
    companion object {
        private var instance: TaskManager? = null
        fun getInstance(): TaskManager {
            if (instance == null) {
                instance = TaskManager()
            }
            return instance!!
        }
    }

    fun addTaskNoResultValue(taskList: List<INoReturnValueTask>) {
        if (taskList.isEmpty()) {
            println("任务列表为空, 结束.")
            return
        }
        // 判断jobs的调度器是否一致 不一致的话抛出异常
        val dispatchers = taskList.map { it.getDispatcher() }.distinct()
        if (dispatchers.size > 1) {
            throw IllegalArgumentException("所有任务调度器必须一致, 当前发现调度器: ${dispatchers.joinToString()}")
        }
        // 获取调度器
        val dispatcher: TDispatchers = taskList[0].getDispatcher()
        taskList.forEach { job ->
            println("添加任务: ${job.getTaskName()} 调度器:$dispatcher -->${System.identityHashCode(this)}")
        }
        when (dispatcher) {
            TDispatchers.Main -> {
                val executor: INoReturnExecutor = MainNoReturnExecutor.getInstance()
                executor.execute(taskList)
            }

            TDispatchers.Serial -> {
                val executor: INoReturnExecutor = SerialNoReturnExecutor.getInstance()
                executor.execute(taskList)
            }

            TDispatchers.Parallel -> {
                val executor: INoReturnExecutor = ParallelNoReturnExecutor.getInstance()
                executor.execute(taskList)
            }
        }
    }

    suspend fun addTaskWithReturnValue(taskList: List<IWithReturnTask<String>>): List<TaskResult<String>> {
        if (taskList.isEmpty()) {
            println("任务列表为空, 结束.")
            return emptyList()
        }
        // 判断jobs的调度器是否一致 不一致的话抛出异常
        val dispatchers = taskList.map { it.getDispatcher() }.distinct()
        if (dispatchers.size > 1) {
            throw IllegalArgumentException("所有任务调度器必须一致, 当前发现调度器: ${dispatchers.joinToString()}")
        }
        // 获取调度器
        val dispatcher: TDispatchers = taskList[0].getDispatcher()
        taskList.forEach { job ->
            println("添加任务: ${job.getTaskName()} 调度器:$dispatcher -->${System.identityHashCode(this)}")
        }
        return when (dispatcher) {
            TDispatchers.Main -> {
                val executor: MainWithReturnExecutor = MainWithReturnExecutor.getInstance()
                executor.execute(taskList)
            }

            TDispatchers.Serial -> {
                val executor: SerialWithReturnExecutor = SerialWithReturnExecutor.getInstance()
                executor.execute(taskList)
            }

            TDispatchers.Parallel -> {
                val executor: ParallelWithReturnExecutor = ParallelWithReturnExecutor.getInstance()
                executor.execute(taskList)
            }

            else -> {
                emptyList()
            }
        }
    }
}