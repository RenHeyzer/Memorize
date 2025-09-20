package dev.renheyzer.memorize.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dev.renheyzer.memorize.core.common.AppDispatchers
import dev.renheyzer.memorize.core.utils.ErrorTypeToErrorTextConverter
import dev.renheyzer.memorize.core.utils.ErrorTypeToErrorTextConverterImpl
import dev.renheyzer.memorize.data.repositories.AuthRepository
import dev.renheyzer.memorize.data.repositories.AuthRepositoryImpl
import dev.renheyzer.memorize.data.sources.AuthRemoteDataSource
import dev.renheyzer.memorize.data.sources.AuthRemoteDataSourceImpl

class AppDependencies {

    val dispatchers by lazy { AppDispatchers() }

    val firebaseAuth = Firebase.auth
    val firebaseFirestore = Firebase.firestore
    val authRemoteDataSource: AuthRemoteDataSource by lazy { AuthRemoteDataSourceImpl(firebaseAuth) }
    val authRepository: AuthRepository by lazy { AuthRepositoryImpl(authRemoteDataSource) }
    val errorTypeToErrorTextConverter: ErrorTypeToErrorTextConverter by lazy { ErrorTypeToErrorTextConverterImpl() }
}