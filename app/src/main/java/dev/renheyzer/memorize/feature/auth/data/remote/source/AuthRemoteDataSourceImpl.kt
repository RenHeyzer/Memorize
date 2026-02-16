package dev.renheyzer.memorize.feature.auth.data.remote.source

import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRemoteDataSourceImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRemoteDataSource {

    override suspend fun registerByEmail(
        email: String,
        password: String
    ): AuthResult? = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signInViaEmailAndPassword(email: String, password: String): AuthResult? =
        firebaseAuth.signInWithEmailAndPassword(email, password).await()

    override suspend fun sendSignInLinkToEmail(
        email: String,
        actionCodeSettings: ActionCodeSettings
    ) {
        firebaseAuth.sendSignInLinkToEmail(email, actionCodeSettings).await()
    }

    override suspend fun signInViaEmailLink(
        email: String,
        emailLink: String
    ): AuthResult? = firebaseAuth.signInWithEmailLink(email, emailLink).await()
}