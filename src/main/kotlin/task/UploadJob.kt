package task

import kotlinx.coroutines.delay

class UploadJob: IJob {
    override fun getJobName(): String {
        return "上传任务"
    }

    override suspend fun execute() {
        println("上传任务开始执行+++")
        delay(1_000)
        println("上传任务执行完毕---")
    }
}