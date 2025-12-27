import kotlinx.coroutines.runBlocking
import manager.TaskManager
import task.withreturn.SendMessageWithReturnTask
import task.withreturn.UploadWithReturnTask
import kotlin.system.measureTimeMillis

fun main() {
    val taskManager = TaskManager.getInstance()

    runBlocking {
        // 测量代码执行时间 毫秒
        val time = measureTimeMillis {
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
        println("time=$time")
    }

}