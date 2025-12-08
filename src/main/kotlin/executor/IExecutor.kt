package org.example.executor

import task.IJob

interface IExecutor {
      fun execute(jobs: List<IJob>)
}