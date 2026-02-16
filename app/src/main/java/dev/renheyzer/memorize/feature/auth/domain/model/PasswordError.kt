package dev.renheyzer.memorize.feature.auth.domain.model

enum class PasswordError {
    TOO_SHORT,
    NO_UPPERCASE,
    NO_DIGIT,
    NO_SPECIAL,
    MISMATCH
}