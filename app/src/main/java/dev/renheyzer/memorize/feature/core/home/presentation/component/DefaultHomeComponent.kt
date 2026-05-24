package dev.renheyzer.memorize.feature.core.home.presentation.component

import com.arkivanov.decompose.ComponentContext

class DefaultHomeComponent(
    componentContext: ComponentContext,
    val onOutput: (HomeComponent.Output) -> Unit
) : HomeComponent, ComponentContext by componentContext {

    override fun onNumbersGameSelected() {
        onOutput(HomeComponent.Output.NavigateToNumbers)
    }
}