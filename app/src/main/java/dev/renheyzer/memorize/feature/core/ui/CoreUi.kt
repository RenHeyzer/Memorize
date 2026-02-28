package dev.renheyzer.memorize.feature.core.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import dev.renheyzer.memorize.core.components.core.CoreRootComponent
import dev.renheyzer.memorize.feature.core.home.presentation.ui.home.HomeScreen
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.NumbersContent

@Composable
fun CoreContent(
    component: CoreRootComponent,
    modifier: Modifier = Modifier
) {
    Children(
        stack = component.childStack,
        modifier = modifier.fillMaxSize(),
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is CoreRootComponent.Child.Home -> HomeScreen(component = child.component)
            is CoreRootComponent.Child.Numbers -> NumbersContent(
                component = child.component,
                onBackClick = component::onBackPressed
            )

            is CoreRootComponent.Child.Pictures -> {}
        }
    }
}