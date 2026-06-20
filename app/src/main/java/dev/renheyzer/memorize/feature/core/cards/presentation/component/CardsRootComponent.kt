package dev.renheyzer.memorize.feature.core.cards.presentation.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.renheyzer.memorize.feature.core.cards.presentation.component.memorization.MemorizationComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.component.recall.RecallComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.component.results.ResultsComponent
import dev.renheyzer.memorize.feature.core.cards.presentation.component.setup.CardsSetupComponent

interface CardsRootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Setup(val component: CardsSetupComponent) : Child()
        class Memorization(val component: MemorizationComponent) : Child()
        class Recall(val component: RecallComponent) : Child()
        class Results(val component: ResultsComponent) : Child()
    }
}