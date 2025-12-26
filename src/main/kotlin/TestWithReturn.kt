import kotlinx.coroutines.runBlocking
import manager.TaskManager
import task.withreturn.SendMessageWithReturnTask
import task.withreturn.UploadWithReturnTask

fun main() {
    val taskManager = TaskManager.getInstance()

    runBlocking {
        val resultList = taskManager.addTaskWithReturnValue(
            listOf(
                UploadWithReturnTask(),
                SendMessageWithReturnTask()
            )
        )
        resultList.forEachIndexed { index, item ->
            println("$index ${item.result}")
        }
    }

}