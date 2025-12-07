package task

interface IJob {
    fun getJobName(): String
    suspend fun execute()
}