package task.noreturn

import kotlinx.coroutines.delay
import dispatcher.TDispatchers
import entity.TaskResult
import event.EventBus
import event.TaskEvent
import task.noreturn.INoReturnValueTask

class UploadNoReturnValueTask: INoReturnValueTask {
    override fun getTaskName(): String {
        return "上传"
    }

    override fun getDispatcher(): TDispatchers {
//        return TDispatchers.Serial
//        return TDispatchers.Main
        return TDispatchers.Parallel
    }

    override suspend fun onRun() {
        println("${getTaskName()}开始+++")
        delay(5_000)
        println("${getTaskName()}结束---")
        println("事件发送==>${getTaskName()}")
        EventBus.notify(
            TaskEvent.FileUploaded(
            filePath = "filePath1",
            taskResult = TaskResult.success(getTaskName(), "file result1")))
    }
}