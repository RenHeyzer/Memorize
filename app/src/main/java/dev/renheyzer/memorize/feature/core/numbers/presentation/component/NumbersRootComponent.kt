package dev.renheyzer.memorize.feature.core.numbers.presentation.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization.NumbersRoundComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.result.ResultComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup.NumbersSetupComponent


interface NumbersRootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Setup(val component: NumbersSetupComponent) : Child()
        class Round(val component: NumbersRoundComponent) : Child()
        class Result(val component: ResultComponent) : Child()
    }
}