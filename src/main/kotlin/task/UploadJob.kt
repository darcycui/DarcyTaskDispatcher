package task

import kotlinx.coroutines.delay
import org.example.dispatcher.TDispatchers

class UploadJob: IJob {
    override fun getJobName(): String {
        return "上传"
    }

    override fun getDispatcher(): TDispatchers {
        return TDispatchers.Default
//        return TDispatchers.Main
    }

    override suspend fun onRun() {
        println("开始执行任务：${getJobName()}")
        delay(10_000)
        println("任务执行完毕：${getJobName()}")
    }
}