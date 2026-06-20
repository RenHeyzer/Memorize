package dev.renheyzer.memorize.feature.core.cards.presentation.component.recall

import com.arkivanov.decompose.ComponentContext
import dev.renheyzer.memorize.core.di.factory.ComponentFactory

fun ComponentFactory.createRecallComponent(
    context: ComponentContext
): RecallComponent = DefaultRecallComponent(
    componentContext = context
)