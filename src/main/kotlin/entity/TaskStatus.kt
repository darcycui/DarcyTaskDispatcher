package org.example.entity

sealed class TaskStatus {
    data object Initial : TaskStatus()
    data object Waiting : TaskStatus()
    data object Running : TaskStatus()
    data object Success : TaskStatus()
    data object Failed : TaskStatus()
}