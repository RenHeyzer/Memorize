package dev.renheyzer.memorize.components.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.components.auth.login.LoginComponent
import dev.renheyzer.memorize.components.auth.registration.RegistrationComponent
import kotlinx.serialization.Serializable

class DefaultAuthComponent(
    componentContext: ComponentContext
) : AuthComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ScreenConfig>()

    override val stack: Value<ChildStack<*, AuthComponent.AuthChild>> =
        childStack(
            source = navigation,
            initialConfiguration = ScreenConfig.Login,
            handleBackButton = true,
            serializer = ScreenConfig.serializer(),
            childFactory = ::childFactory
        )

    private fun childFactory(
        config: ScreenConfig,
        componentContext: ComponentContext
    ): AuthComponent.AuthChild =
        when (config) {
            is ScreenConfig.Registration -> AuthComponent.AuthChild.RegistrationChild(
                RegistrationComponent(
                    componentContext
                )
            )
            is ScreenConfig.Login -> AuthComponent.AuthChild.LoginChild(
                LoginComponent(
                    componentContext
                )
            )
        }
}

@Serializable
private sealed interface ScreenConfig {
    @Serializable
    data object Registration : ScreenConfig

    @Serializable
    data object Login : ScreenConfig
}