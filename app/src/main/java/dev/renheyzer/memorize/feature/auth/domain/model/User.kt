package dev.renheyzer.memorize.feature.auth.domain.model

import android.net.Uri

data class User(
    val id: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val photo: Uri,
    val isEmailVerified: Boolean = false,
)