package task.withreturn

import kotlinx.coroutines.delay
import dispatcher.TDispatchers
import entity.TaskResult
import entity.TaskStatus
import task.withreturn.IWithReturnTask

class SendMessageWithReturnTask : IWithReturnTask<String> {
    override fun getTaskName(): String {
        return "发送消息（带返回值）"
    }

    override fun getDispatcher(): TDispatchers {
        return TDispatchers.Main
//        return TDispatchers.Serial
//        return TDispatchers.Parallel
    }

    override suspend fun onRun(): TaskResult<String> {
        delay(2_000)
        return TaskResult(
            taskName = getTaskName(),
            status = TaskStatus.Success,
            result = "SendMessage Success: message=message1"
        )
    }
}