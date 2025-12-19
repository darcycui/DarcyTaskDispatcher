package entity

data class TaskResult<T>(
    val taskName: String,
    val status: TaskStatus = TaskStatus.Initial,
    val error: Throwable? = null,
    val errorMessage: String = error?.message ?: "",
    val result: T? = null,
) {
    companion object {
        fun <T> success(taskName: String, result: T? = null): TaskResult<T> {
            return TaskResult(
                taskName = taskName,
                status = TaskStatus.Success,
                result = result
            )
        }

        fun <T> failed(
            taskName: String,
            error: Throwable? = null,
            errorMessage: String = error?.message ?: "Unknown Error"
        ): TaskResult<T> {
            return TaskResult(
                taskName = taskName,
                status = TaskStatus.Failed,
                error = error,
                errorMessage = errorMessage
            )
        }
    }

    fun isSuccess(): Boolean {
        return status == TaskStatus.Success
    }

    fun isFailed(): Boolean {
        return status == TaskStatus.Failed
    }

    override fun toString(): String {
        return "TaskResult(taskName='$taskName', status=$status, error=$error, errorMessage='$errorMessage', result=$result)"
    }
}