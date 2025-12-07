package org.example.task

import task.IJob

class SendMessageJob: IJob {
    override fun getJobName(): String {
        return ""
    }

    override suspend fun execute() {
        TODO("Not yet implemented")
    }
}