package org.example.entity

data class TaskError(
    val taskName: String,
    val taskStatus: TaskStatus = TaskStatus.Failed,
    val error: Throwable,
    val errorMessage: String = error.message ?: "Unknown error"
)
