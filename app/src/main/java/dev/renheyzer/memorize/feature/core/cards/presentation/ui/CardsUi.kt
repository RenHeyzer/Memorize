package dev.renheyzer.memorize.feature.core.cards.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import dev.renheyzer.memorize.feature.core.cards.presentation.component.CardsRootComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.ui.memorization.MemorizationScreen
import dev.renheyzer.memorize.feature.core.cards.presentation.ui.recall.RecallScreen
import dev.renheyzer.memorize.feature.core.cards.presentation.ui.results.ResultsScreen
import dev.renheyzer.memorize.feature.core.cards.presentation.ui.setup.CardsSetupScreen

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
            is CardsRootComponent.Child.Setup -> CardsSetupScreen(
                component = child.component,
                onBackClick = onBackClick
            )
            is CardsRootComponent.Child.Memorization -> MemorizationScreen(
                component = child.component,
                onBackClick = onBackClick
            )

            is CardsRootComponent.Child.Recall -> RecallScreen(
                component = child.component,
                onBackClick = onBackClick
            )

            is CardsRootComponent.Child.Results -> ResultsScreen(
                component = child.component,
                onBackClick = onBackClick
            )
        }
    }
}