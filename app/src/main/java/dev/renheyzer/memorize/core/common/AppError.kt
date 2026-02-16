package dev.renheyzer.memorize.core.common

interface AppError

sealed interface NetworkError : AppError {
    data object NoInternet : NetworkError
    data object ServerError : NetworkError
    data object ServiceUnavailable : NetworkError
    data object Timeout : NetworkError
    data object Unauthorized : NetworkError
    data class Unknown(val cause: Throwable?) : NetworkError
}