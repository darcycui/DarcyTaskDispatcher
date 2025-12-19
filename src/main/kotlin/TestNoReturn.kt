import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import event.EventBus
import event.TaskEvent
import manager.TaskManager
import task.noreturn.SendMessageNoReturnValueTask
import task.noreturn.UploadNoReturnValueTask

fun main(args: Array<String>) {
    val taskManager = TaskManager.getInstance()

    taskManager.addTaskNoResultValue(listOf(UploadNoReturnValueTask(), SendMessageNoReturnValueTask()))
    runBlocking {
        EventBus.collectEvent(Dispatchers.Default) {
            when (it) {
                is TaskEvent.FileUploaded<*> -> println("事件接收<== $it")
                is TaskEvent.MessageSent<*> -> println("事件接收<== $it")
            }
        }
    }
}