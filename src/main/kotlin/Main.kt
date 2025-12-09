package org.example

import kotlinx.coroutines.runBlocking
import org.example.manager.TaskManager
import org.example.task.SendMessageJob
import task.UploadJob

fun main() {
    val taskManager = TaskManager.getInstance()
    println("当前线程: ${Thread.currentThread().name} aaa")
//    taskManager.addJob(UploadJob())
//    println("当前线程: ${Thread.currentThread().name} bbb")
//    taskManager.addJob(SendMessageJob())

    val results = taskManager.addJob(listOf(UploadJob(),SendMessageJob()))
    println("结果: $results")
    println("当前线程: ${Thread.currentThread().name} ccc")
    Thread.sleep(20_000)
}