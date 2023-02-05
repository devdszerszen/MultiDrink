package pl.dszerszen.multidrink.android.ui.base

import kotlinx.coroutines.channels.Channel
import pl.dszerszen.multidrink.android.ui.error.BottomInfoMessageType
import pl.dszerszen.multidrink.android.ui.error.BottomInfoMessageType.ERROR
import pl.dszerszen.multidrink.domain.AppException

interface InAppEventDispatcher {
    fun dispatchEvent(event: InAppEvent)
}

interface InAppEventHandler {
    suspend fun handleEvent(onEvent: (InAppEvent) -> Unit)
}

object InAppComponent : InAppEventDispatcher, InAppEventHandler {
    private val inAppEventsChannel = Channel<InAppEvent>(Channel.CONFLATED)

    override fun dispatchEvent(event: InAppEvent) {
        inAppEventsChannel.trySend(event)
    }

    override suspend fun handleEvent(onEvent: (InAppEvent) -> Unit) {
        for (event in inAppEventsChannel) {
            onEvent.invoke(event)
        }
    }
}

sealed class InAppEvent {
    class Navigate(val target: NavScreen, val argument: String?) : InAppEvent()
    object NavigateBack : InAppEvent()
    class BottomInfo(
        val title: String,
        val desc: String?,
        val type: BottomInfoMessageType
    ) : InAppEvent()
}

fun InAppEventDispatcher.navigate(target: NavScreen, argument: String?) =
    dispatchEvent(InAppEvent.Navigate(target, argument))

fun InAppEventDispatcher.navigateBack() = dispatchEvent(InAppEvent.NavigateBack)

fun InAppEventDispatcher.showInfo(
    title: String,
    desc: String?,
    type: BottomInfoMessageType
) = dispatchEvent(InAppEvent.BottomInfo(title, desc, type))

fun InAppEventDispatcher.handleException(exception: AppException) =
    dispatchEvent(InAppEvent.BottomInfo(exception.message, exception.cause?.message, ERROR))