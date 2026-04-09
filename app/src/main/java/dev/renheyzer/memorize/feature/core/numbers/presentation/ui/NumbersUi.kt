package dev.renheyzer.memorize.feature.core.numbers.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import dev.renheyzer.memorize.core.components.core.numbers.NumbersRootComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.memorization.MemorizationScreen
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.recall.RecallScreen
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.results.ResultsScreen
import dev.renheyzer.memorize.zeature.ui.NumbersSetupScreen

@Composable
fun NumbersContent(
    modifier: Modifier = Modifier,
    component: NumbersRootComponent,
    onBackClick: () -> Unit
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is NumbersRootComponent.Child.Setup -> NumbersSetupScreen(
                component = child.component,
                onBackClick = onBackClick
            )

            is NumbersRootComponent.Child.Memorization -> MemorizationScreen(
                component = child.component,
                onBackClick = onBackClick
            )

            is NumbersRootComponent.Child.Recall -> RecallScreen(
                component = child.component,
                onBackClick = onBackClick
            )

            is NumbersRootComponent.Child.Results -> ResultsScreen(
                component = child.component,
                onBackClick = onBackClick
            )
        }
    }
}