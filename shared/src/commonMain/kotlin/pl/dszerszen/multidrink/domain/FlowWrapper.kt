package pl.dszerszen.multidrink.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

fun <T : Any> Flow<T>.wrapped() = FlowWrapper(this)

class FlowWrapper<T : Any>(private val wrapped: Flow<T>) {
    fun handleIos(onNext: (T) -> Unit, onComplete: (cause: AppException?) -> Unit): Cancellable {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        scope.launch {
            try {
                wrapped.collect {
                    onNext(it)
                }
                onComplete(null)
            } catch (cancellationException: CancellationException) {
                onComplete(null)
            } catch (th: Throwable) {
                onComplete(th.mapToError())
            }
        }
        return object : Cancellable {
            override fun cancel() {
                scope.cancel()
            }
        }
    }

    fun handleAndroid() = wrapped
}