package event

import entity.TaskResult

sealed class TaskEvent {
    data class FileUploaded<T>(val filePath: String, val taskResult: TaskResult<T>) : TaskEvent() {
        override fun toString(): String {
            return "FileUploaded(filePath='$filePath', taskResult=$taskResult)"
        }
    }
    data class MessageSent<T>(val message: String, val taskResult: TaskResult<T>) : TaskEvent()
}