package pl.dszerszen.multidrink.domain

sealed class AppException(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause) {
    data class NetworkException(override val message: String) : AppException(message)
    data class UnknownException(override val cause: Throwable) : AppException("Unknown error", cause)
}

fun Throwable.mapToError(): AppException {
    return when (this) {
        is AppException -> this
        else -> AppException.UnknownException(this)
    }
}