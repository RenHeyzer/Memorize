package dev.renheyzer.memorize.components.home

import com.arkivanov.decompose.ComponentContext

class HomeComponent(
    componentContext: ComponentContext
) : Home, ComponentContext by componentContext {


}