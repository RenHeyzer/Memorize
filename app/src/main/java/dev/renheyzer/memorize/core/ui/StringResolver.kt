package dev.renheyzer.memorize.core.ui

interface StringResolver {
    fun resolve(text: UiText): String
}