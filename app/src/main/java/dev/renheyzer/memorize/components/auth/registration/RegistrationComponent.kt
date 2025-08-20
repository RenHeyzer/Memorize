package dev.renheyzer.memorize.components.auth.registration

import com.arkivanov.decompose.ComponentContext

class RegistrationComponent(
    componentContext: ComponentContext
) : Registration, ComponentContext by componentContext {

}