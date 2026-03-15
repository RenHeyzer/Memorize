package dev.renheyzer.memorize.core.components.core.numbers

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.core.components.core.numbers.memorization.MemorizationComponent
import dev.renheyzer.memorize.core.components.core.numbers.recall.RecallComponent


interface NumbersRootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Memorization(val component: MemorizationComponent) : Child()
        class Recall(val component: RecallComponent) : Child()
    }
}