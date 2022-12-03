package pl.dszerszen.multidrink.domain

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Failure<T>(val error: Error) : Response<T>()
}

fun <S, R> Response<S>.fold(
    onSuccess: (S) -> R,
    onFail: (Error) -> R
): R {
    return when (this) {
        is Response.Success -> onSuccess(data)
        is Response.Failure -> onFail(error)
    }
}