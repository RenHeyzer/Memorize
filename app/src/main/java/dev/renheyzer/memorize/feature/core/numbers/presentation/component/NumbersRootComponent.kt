package dev.renheyzer.memorize.feature.core.numbers.presentation.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.memorization.MemorizationComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.recall.RecallComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.result.ResultsComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.setup.NumbersSetupComponent


interface NumbersRootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Setup(val component: NumbersSetupComponent): Child()
        class Memorization(val component: MemorizationComponent) : Child()
        class Recall(val component: RecallComponent) : Child()
        class Results(val component: ResultsComponent) : Child()
    }
}