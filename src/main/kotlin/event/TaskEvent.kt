package event

import entity.TaskResult

sealed class TaskEvent(val eventName: String) {
    object EmptyEvent : TaskEvent("empty event")
    data class FileUpload<T>(val filePath: String, val taskResult: TaskResult<T>) : TaskEvent("FileUploaded") {
        override fun toString(): String {
            return "FileUploaded(filePath='$filePath', taskResult=$taskResult)"
        }
    }

    data class MessageSend<T>(val message: String, val taskResult: TaskResult<T>) : TaskEvent("MessageSend")
}