package dev.renheyzer.memorize.core.components.core.home

import com.arkivanov.decompose.ComponentContext

class DefaultHomeComponent(
    componentContext: ComponentContext,
    val onOutput: (HomeComponent.Output) -> Unit
) : HomeComponent, ComponentContext by componentContext {

    override fun onNumbersGameSelected() {
        onOutput(HomeComponent.Output.NavigateToNumbers)
    }
}