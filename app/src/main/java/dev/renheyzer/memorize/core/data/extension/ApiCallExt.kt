package dev.renheyzer.memorize.core.data.extension

import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.core.common.Either
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Either<NetworkError, T> {
    return try {
        val result = apiCall()
        Either.Right(result)
    } catch (e: Exception) {
        val error = e.toNetworkType()
        Either.Left(error)
    }
}