package dev.renheyzer.memorize.feature.root.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import dev.renheyzer.memorize.core.components.root.RootComponent
import dev.renheyzer.memorize.feature.auth.presentation.ui.AuthContent
import dev.renheyzer.memorize.feature.main.presentation.ui.home.HomeScreen

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Home -> HomeScreen(
                component = child.component,
                modifier = Modifier.fillMaxSize()
            )

            is RootComponent.Child.Auth -> AuthContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}