package task

import kotlinx.coroutines.delay
import org.example.dispatcher.TDispatchers
import org.example.entity.JobResult

class UploadJob: IJob {
    override fun getJobName(): String {
        return "上传"
    }

    override fun getDispatcher(): TDispatchers {
//        return TDispatchers.Serial
        return TDispatchers.Main
//        return TDispatchers.Parallel
    }

    override suspend fun onRun() {
        println("开始：${getJobName()}")
        delay(10_000)
        println("结束：${getJobName()}")
    }
}