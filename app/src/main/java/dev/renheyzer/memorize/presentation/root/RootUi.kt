package dev.renheyzer.memorize.presentation.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import dev.renheyzer.memorize.components.root.RootComponent
import dev.renheyzer.memorize.presentation.auth.AuthContent
import dev.renheyzer.memorize.presentation.home.HomeScreen

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
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
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}