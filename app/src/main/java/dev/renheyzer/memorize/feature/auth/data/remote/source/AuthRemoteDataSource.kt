package dev.renheyzer.memorize.feature.auth.data.remote.source

import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRemoteDataSource {

    suspend fun registerByEmail(email: String, password: String): AuthResult?

    val currentUser: FirebaseUser
    val currentUserOrNull: FirebaseUser?

    suspend fun signInViaEmailAndPassword(email: String, password: String): AuthResult?
    suspend fun sendSignInLinkToEmail(email: String, actionCodeSettings: ActionCodeSettings)
    suspend fun sendEmailVerification(settings: ActionCodeSettings)
    suspend fun signInViaEmailLink(email: String, emailLink: String): AuthResult?
    suspend fun onDeepLinkReceived(code: String)
}