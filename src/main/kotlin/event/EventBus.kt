package event

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

object EventBus {
    // 内部使用
    private val _eventFlow = MutableStateFlow<TaskEvent?>(
        value = null
//        // 新订阅者不接收旧事件（非粘性）
//        replay = 0,
//        // 配置缓冲区大小
//        extraBufferCapacity = 64
    )

    // 发送事件到所有订阅者
    suspend fun notify(event: TaskEvent) {
//        _eventFlow.emit(event)
        _eventFlow.value = event
    }

//    suspend fun notify(events: List<TaskEvent>) {
//        _eventFlow.emitAll(events.asFlow())
//    }
//
//    // 在协程外发送事件可以使用tryEmit，但需注意缓冲区情况
//    fun tryNotify(event: TaskEvent): Boolean {
//        return _eventFlow.tryEmit(event)
//    }

    // 订阅事件
    suspend fun collectEvent(dispatcher: CoroutineDispatcher, action: (TaskEvent) -> Unit) {
        _eventFlow.collect {taskEvent->
            // 处理事件
            withContext(dispatcher) {
                taskEvent?.let {
                    action(taskEvent)
                } ?: run {
                    println("错误：taskEvent为空")
                }
            }
        }
    }
}