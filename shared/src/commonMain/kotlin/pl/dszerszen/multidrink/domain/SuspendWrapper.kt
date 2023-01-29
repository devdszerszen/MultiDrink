package pl.dszerszen.multidrink.domain

import kotlinx.coroutines.*

interface Cancellable {
    fun cancel()
}

class SuspendWrapper<T : Any>(private val wrapped: suspend () -> T) {
    fun handleIos(
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) : Cancellable {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        scope.launch {
            try {
                onSuccess(wrapped())
            }
            catch (cancellationException: CancellationException) {
                //do nothing
            }
            catch (th: Throwable) {
                onError(th)
            }
        }
        return object : Cancellable {
            override fun cancel() {
                scope.cancel()
            }
        }
    }

    suspend fun handleAndroid() = wrapped()
}

fun <T : Any> handleSuspend(action: suspend () -> T) : SuspendWrapper<T> = SuspendWrapper(action)