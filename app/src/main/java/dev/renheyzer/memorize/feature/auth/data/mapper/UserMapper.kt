package dev.renheyzer.memorize.feature.auth.data.mapper

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import dev.renheyzer.memorize.feature.auth.domain.model.User

fun FirebaseUser?.toDomain() = User(
    id = this?.uid ?: "",
    username = this?.displayName ?: "User-${this?.uid?.take(6)}",
    email = this?.email ?: "",
    phoneNumber = this?.phoneNumber ?: "",
    photo = this?.photoUrl ?: Uri.EMPTY,
    isEmailVerified = this?.isEmailVerified ?: false
)