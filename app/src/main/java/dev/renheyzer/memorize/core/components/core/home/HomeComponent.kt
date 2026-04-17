package dev.renheyzer.memorize.core.components.core.home

interface HomeComponent {

    sealed interface Output {
        data object NavigateToPictures : Output
        data object NavigateToNumbers : Output
    }

    fun onNumbersGameSelected()
}