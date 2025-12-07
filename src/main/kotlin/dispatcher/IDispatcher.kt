package org.example.dispatcher

import task.IJob

interface IDispatcher {
    suspend fun dispatch(job: IJob)
}