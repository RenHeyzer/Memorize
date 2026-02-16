package dev.renheyzer.memorize.core.components.auth.login

import com.arkivanov.decompose.ComponentContext

class LoginComponent(
    componentContext: ComponentContext
) : Login, ComponentContext by componentContext {

}