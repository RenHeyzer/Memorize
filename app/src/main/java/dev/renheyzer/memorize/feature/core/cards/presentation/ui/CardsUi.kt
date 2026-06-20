package dev.renheyzer.memorize.feature.core.cards.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import dev.renheyzer.memorize.feature.core.cards.presentation.component.CardsRootComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.ui.memorization.MemorizationScreen

@Composable
fun CardsContent(
    modifier: Modifier = Modifier,
    component: CardsRootComponent,
    onBackClick: () -> Unit
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is CardsRootComponent.Child.Memorization -> MemorizationScreen(
                component = child.component,
                onBackClick = onBackClick
            )

            else -> {}
        }
    }
}