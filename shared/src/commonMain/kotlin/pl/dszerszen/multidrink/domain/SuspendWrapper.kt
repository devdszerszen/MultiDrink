package pl.dszerszen.multidrink.domain

import kotlinx.coroutines.*

interface Cancellable {
    fun cancel()
}

class SuspendWrapper<T : Any>(private val wrapped: suspend () -> T) {
    fun handleIos(
        onSuccess: (T) -> Unit,
        onError: (AppException) -> Unit
    ): Cancellable {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        scope.launch {
            try {
                onSuccess(wrapped())
            } catch (ignored: CancellationException) {
                //do nothing
            } catch (th: Throwable) {
                onError(th.mapToError())
            }
        }
        return object : Cancellable {
            override fun cancel() {
                scope.cancel()
            }
        }
    }

    suspend fun handleAndroid(onError: (AppException) -> Unit) : T? = try {
        wrapped()
    } catch (ignored: CancellationException) {
        //do nothing
        null
    } catch (th: Throwable) {
        onError(th.mapToError())
        null
    }
}

fun <T : Any> handleSuspend(action: suspend () -> T): SuspendWrapper<T> = SuspendWrapper(action)