package dev.renheyzer.memorize.feature.core.numbers.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.NumbersRootComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.result.ResultScreen
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.round.RoundScreen
import dev.renheyzer.memorize.feature.core.numbers.presentation.ui.setup.NumbersSetupScreen

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

            is NumbersRootComponent.Child.Round -> RoundScreen(
                component = child.component,
            )

            is NumbersRootComponent.Child.Result -> ResultScreen(
                component = child.component,
                onBackClick = onBackClick
            )
        }
    }
}