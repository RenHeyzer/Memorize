package dev.renheyzer.memorize.core.models

data class AuthConfig(
    val deepLinkUrl: String,
    val minAppSdkVersion: String = "1",
    val installIfNotAvailable: Boolean = true
)