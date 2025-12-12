package org.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.example.event.EventBus
import org.example.event.TaskEvent
import org.example.manager.TaskManager
import org.example.task.SendMessageJob
import task.UploadJob

fun main() {
    val taskManager = TaskManager.getInstance()
    println("当前线程: ${Thread.currentThread().name} aaa")
//    taskManager.addJob(UploadJob())
//    println("当前线程: ${Thread.currentThread().name} bbb")
//    taskManager.addJob(SendMessageJob())

    taskManager.addJob(listOf(UploadJob(),SendMessageJob()))
    println("当前线程: ${Thread.currentThread().name} ccc")
    runBlocking {
        EventBus.collectEvent(Dispatchers.Default){
            when(it){
                is TaskEvent.FileUploaded<*> -> println("事件接收<== $it")
                is TaskEvent.MessageSent<*> -> println("事件接收<== $it")
            }
        }
    }

    Thread.sleep(20_000)
}