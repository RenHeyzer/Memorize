package dev.renheyzer.memorize.core.data.extension

import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.core.common.NetworkError
import kotlinx.coroutines.CancellationException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Either<NetworkError, T> {
    return try {
        val result = apiCall()
        Either.Right(result)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        val error = e.toNetworkType()
        Either.Left(error)
    }
}