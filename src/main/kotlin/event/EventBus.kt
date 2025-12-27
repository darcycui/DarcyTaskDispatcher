package event

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

@ExperimentalCoroutinesApi
object EventBus {
    // 内部使用
    private val _fileUploadedEventFlow = MutableStateFlow<TaskEvent>(
        value = TaskEvent.EmptyEvent
//        // 新订阅者不接收旧事件（非粘性）
//        replay = 0,
//        // 配置缓冲区大小
//        extraBufferCapacity = 64 
    )
    private val _messageSentEventFlow = MutableStateFlow<TaskEvent>(value = TaskEvent.EmptyEvent)
    private val map: Map<KClass<*>, MutableStateFlow<TaskEvent>> = mapOf(
        TaskEvent.FileUpload::class to _fileUploadedEventFlow,
        TaskEvent.MessageSend::class to _messageSentEventFlow
    )

    // 发送事件到所有订阅者
    suspend fun notify(event: TaskEvent) {
//        _eventFlow.emit(event)
//        _fileUploadEventFlow.value = event
        map[event::class]?.value = event
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
    suspend fun collectFileUploadedEvent(dispatcher: CoroutineDispatcher, action: (TaskEvent) -> Unit) {
        _fileUploadedEventFlow.collect { taskEvent ->
            // 处理事件
            withContext(dispatcher) {
                if (taskEvent == TaskEvent.EmptyEvent) {
                    return@withContext
                }
                action(taskEvent)
            }
        }
    }

    suspend fun collectMessageSentEvent(dispatcher: CoroutineDispatcher, action: (TaskEvent) -> Unit) {
        _messageSentEventFlow.collect { taskEvent ->
            withContext(dispatcher) {
                if (taskEvent == TaskEvent.EmptyEvent) {
                    return@withContext
                }
                action(taskEvent)
            }
        }
    }

    suspend fun collectCombine(dispatcher: CoroutineDispatcher, action: (String) -> Unit) {
        combine(_fileUploadedEventFlow, _messageSentEventFlow) { fileUploadEvent, messageSendEvent ->
            if (fileUploadEvent == TaskEvent.EmptyEvent || messageSendEvent == TaskEvent.EmptyEvent) {
                ""
            } else {
                "${fileUploadEvent.eventName}---Combine---${messageSendEvent.eventName}"
            }
        }.collect {
            if (it.isEmpty()) {
                return@collect
            }
            withContext(dispatcher) {
                action(it)
            }
        }
    }

    suspend fun collectZip(dispatcher: CoroutineDispatcher, action: (String) -> Unit) {
        _fileUploadedEventFlow.zip(_messageSentEventFlow) { fileUploadEvent, messageSendEvent ->
            if (fileUploadEvent == TaskEvent.EmptyEvent || messageSendEvent == TaskEvent.EmptyEvent) {
                ""
            } else {
                "${fileUploadEvent.eventName}---Zip---${messageSendEvent.eventName}"
            }
        }.collect {
            if (it.isEmpty()) {
                return@collect
            }
            action(it)
        }
    }
}