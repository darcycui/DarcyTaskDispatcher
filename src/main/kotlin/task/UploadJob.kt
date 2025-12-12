package task

import kotlinx.coroutines.delay
import org.example.dispatcher.TDispatchers
import org.example.entity.TaskResult
import org.example.event.EventBus
import org.example.event.TaskEvent

class UploadJob: IJob {
    override fun getJobName(): String {
        return "上传"
    }

    override fun getDispatcher(): TDispatchers {
//        return TDispatchers.Serial
//        return TDispatchers.Main
        return TDispatchers.Parallel
    }

    override suspend fun onRun() {
        println("${getJobName()}开始+++")
        delay(5_000)
        println("${getJobName()}结束---")
        println("事件发送==>${getJobName()}")
        EventBus.notify(TaskEvent.FileUploaded(
            filePath = "filePath1",
            taskResult = TaskResult.success(getJobName(), "file result1")))
    }
}