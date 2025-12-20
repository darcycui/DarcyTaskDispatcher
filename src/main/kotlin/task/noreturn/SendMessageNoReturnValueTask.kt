package task.noreturn

import kotlinx.coroutines.delay
import dispatcher.TDispatchers
import entity.TaskResult
import event.EventBus
import event.TaskEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SendMessageNoReturnValueTask : INoReturnValueTask {
    override fun getTaskName(): String {
        return "发送消息"
    }

    override fun getDispatcher(): TDispatchers {
//        return TDispatchers.Serial
//        return TDispatchers.Main
        return TDispatchers.Parallel
    }


    @ExperimentalCoroutinesApi
    override suspend fun onRun() {
        println("${getTaskName()}开始+++")
        delay(1_000)
        println("${getTaskName()}结束---")
        println("事件发送==>${getTaskName()}")
        EventBus.notify(
            TaskEvent.MessageSend(
                "message1",
                TaskResult.success(getTaskName(), "message result1"),
            )
        )
    }
}