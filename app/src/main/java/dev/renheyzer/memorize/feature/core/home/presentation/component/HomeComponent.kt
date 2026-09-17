package dev.renheyzer.memorize.feature.core.home.presentation.component

interface HomeComponent {

    sealed interface Output {
        data object NavigateToNumbers : Output
        data object NavigateToCards : Output
        data object NavigateToStatistics: Output
    }

    fun onNumbersGameSelected()
    fun onCardsGameSelected()
    fun onStatisticsClicked()
}