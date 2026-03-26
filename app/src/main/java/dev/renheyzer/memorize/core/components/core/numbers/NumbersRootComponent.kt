package dev.renheyzer.memorize.core.components.core.numbers

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.core.components.core.numbers.memorization.MemorizationComponent
import dev.renheyzer.memorize.core.components.core.numbers.recall.RecallComponent
import dev.renheyzer.memorize.core.components.core.numbers.result.ResultsComponent


interface NumbersRootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Memorization(val component: MemorizationComponent) : Child()
        class Recall(val component: RecallComponent) : Child()
        class Results(val component: ResultsComponent) : Child()
    }
}