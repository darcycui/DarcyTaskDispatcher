package org.example.task

import kotlinx.coroutines.delay
import org.example.dispatcher.TDispatchers
import task.IJob

class SendMessageJob: IJob {
    override fun getJobName(): String {
        return "发送消息"
    }

    override fun getDispatcher(): TDispatchers {
//        return TDispatchers.Serial
//        return TDispatchers.Main
        return TDispatchers.Parallel
    }


    override suspend fun onRun() {
        println("开始执行任务：${getJobName()}")
        delay(1_000)
        println("任务执行完毕：${getJobName()}")
    }
}