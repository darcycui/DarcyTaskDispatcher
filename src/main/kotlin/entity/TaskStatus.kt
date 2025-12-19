package entity

sealed class TaskStatus {
    object Initial : TaskStatus()
    object Waiting : TaskStatus()
    object Running : TaskStatus()
    object Success : TaskStatus()
    object Failed : TaskStatus()
}