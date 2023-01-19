package pl.dszerszen.multidrink.domain

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Failure<T>(val error: Error) : Response<T>()

    fun <R> fold(
        onSuccess: (value: T) -> R,
        onFailure: (error: Error) -> R
    ): R {
        return when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(error)
        }
    }
}