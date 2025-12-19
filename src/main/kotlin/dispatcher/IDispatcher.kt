package dispatcher

import task.noreturn.INoReturnValueTask

interface IDispatcher {
    suspend fun dispatch(job: INoReturnValueTask)
}