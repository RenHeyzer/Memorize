package dev.renheyzer.memorize.core.data.extension

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestoreException
import dev.renheyzer.memorize.BuildConfig
import dev.renheyzer.memorize.core.common.NetworkError
import java.io.IOException

fun Throwable.toNetworkType(): NetworkError = when (this) {
    is IOException -> NetworkError.NoInternet
    is FirebaseNetworkException -> NetworkError.NoInternet
    is FirebaseTooManyRequestsException -> NetworkError.ServiceUnavailable
    is FirebaseFirestoreException -> {
        when (this.code) {
            FirebaseFirestoreException.Code.UNAVAILABLE -> NetworkError.NoInternet
            FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> NetworkError.Timeout
            else -> NetworkError.Unknown(this.cause)
        }
    }

    is FirebaseAuthInvalidUserException -> NetworkError.Unauthorized
    else -> {
        if (BuildConfig.DEBUG) {
            throw this
        }
        NetworkError.Unknown(this.cause)
    }
}
