package pl.dszerszen.multidrink.domain

sealed class Error(open val message: String) {
    data class NetworkError(
        override val message: String = "Unknown error"
    ) : Error(message)
}