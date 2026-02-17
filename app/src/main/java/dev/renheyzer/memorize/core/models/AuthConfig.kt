package dev.renheyzer.memorize.core.models

data class AuthConfig(
    val deepLinkUrl: String,
    val minAppSdkVersion: String = "24",
    val installIfNotAvailable: Boolean = false
)