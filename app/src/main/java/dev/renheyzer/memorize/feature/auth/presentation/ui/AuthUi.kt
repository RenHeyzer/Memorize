package dev.renheyzer.memorize.feature.auth.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import dev.renheyzer.memorize.core.components.auth.AuthComponent
import dev.renheyzer.memorize.feature.auth.presentation.ui.login.LoginScreen
import dev.renheyzer.memorize.feature.auth.presentation.ui.registration.RegistrationScreen
import dev.renheyzer.memorize.feature.auth.presentation.ui.verification.VerificationScreen

@Composable
fun AuthContent(
    component: AuthComponent,
    modifier: Modifier,
) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is AuthComponent.AuthChild.RegistrationChild -> RegistrationScreen(
                component = child.component,
            )

            is AuthComponent.AuthChild.VerificationChild -> VerificationScreen(
                component = child.component,
            )

            is AuthComponent.AuthChild.LoginChild -> LoginScreen(
                component = child.component
            )
        }
    }
}