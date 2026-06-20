package dev.renheyzer.memorize.feature.core.cards.presentation.component.recall

import com.arkivanov.decompose.ComponentContext

class DefaultRecallComponent(
    componentContext: ComponentContext,
): ComponentContext by componentContext, RecallComponent {
}