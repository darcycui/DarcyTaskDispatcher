package org.example.task

import kotlinx.coroutines.delay
import org.example.dispatcher.TDispatchers
import org.example.entity.TaskResult
import org.example.entity.TaskStatus
import org.example.event.EventBus
import org.example.event.TaskEvent
import task.IJob

class SendMessageJob : IJob {
    override fun getJobName(): String {
        return "发送消息"
    }

    override fun getDispatcher(): TDispatchers {
//        return TDispatchers.Serial
//        return TDispatchers.Main
        return TDispatchers.Parallel
    }


    override suspend fun onRun() {
        println("${getJobName()}开始+++")
        delay(1_000)
        println("${getJobName()}结束---")
        println("事件发送==>${getJobName()}")
        EventBus.notify(
            TaskEvent.MessageSent(
                "message1",
                TaskResult.success(getJobName(), "message result1"),
            )
        )
    }
}