import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import event.EventBus
import event.TaskEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import manager.TaskManager
import task.noreturn.SendMessageNoReturnValueTask
import task.noreturn.UploadNoReturnValueTask

@ExperimentalCoroutinesApi
fun main(args: Array<String>) {
    val taskManager = TaskManager.getInstance()

    taskManager.addTaskNoResultValue(listOf(UploadNoReturnValueTask(), SendMessageNoReturnValueTask()))
    runBlocking {
//        launch {
//            EventBus.collectFileUploadedEvent(Dispatchers.Default) {
//                println("事件接收<== $it")
//            }
//        }
//        launch {
//            EventBus.collectFileUploadedEvent(Dispatchers.Default) {
//                println("事件接收<== $it")
//            }
//        }
//        launch {
//            EventBus.collectCombine(Dispatchers.Default) {
//                println("事件接收<==：$it")
//            }
//        }
        launch {
            EventBus.collectZip(Dispatchers.Default) {
                println("事件接收<==：$it")
            }
        }
    }
}