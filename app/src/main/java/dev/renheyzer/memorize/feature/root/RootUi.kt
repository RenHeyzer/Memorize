package dev.renheyzer.memorize.feature.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import dev.renheyzer.memorize.feature.auth.presentation.ui.AuthContent
import dev.renheyzer.memorize.feature.core.presenatation.ui.CoreContent

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.stack,
        modifier = modifier.fillMaxSize(),
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Core -> CoreContent(
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