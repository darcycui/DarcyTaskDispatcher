package org.example.event

import org.example.entity.TaskResult
import org.example.entity.TaskStatus

sealed class TaskEvent {
    data class FileUploaded<T>(val filePath: String, val taskResult: TaskResult<T>) : TaskEvent() {
        override fun toString(): String {
            return "FileUploaded(filePath='$filePath', taskResult=$taskResult)"
        }
    }
    data class MessageSent<T>(val message: String, val taskResult: TaskResult<T>) : TaskEvent()
}