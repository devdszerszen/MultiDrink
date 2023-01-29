package pl.dszerszen.multidrink.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion

fun <T : Any> Flow<T>.wrapped() = FlowWrapper(this)

class FlowWrapper<T : Any>(private val wrapped: Flow<T>) {
    fun handleIos(onNext: (T) -> Unit, onComplete: (cause: Throwable?) -> Unit): Cancellable {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        scope.launch {
            try {
                wrapped.collect {
                    onNext(it)
                }
                onComplete(null)
            } catch (cancellationException: CancellationException) {
                //do nothing
            } catch (th: Throwable) {
                onComplete(th)
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