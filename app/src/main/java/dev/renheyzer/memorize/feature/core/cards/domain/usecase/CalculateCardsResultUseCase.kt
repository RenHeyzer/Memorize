package dev.renheyzer.memorize.feature.core.cards.domain.usecase

import dev.renheyzer.memorize.feature.core.cards.domain.model.AnswerResult
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsAnswer
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsParam
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsResult
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsTask

class CalculateCardsResultUseCase {
    operator fun invoke(
        params: CardsParam,
        task: CardsTask,
        answer: CardsAnswer,
        startedAtMillis: Long,
        recallStartedAtMillis: Long,
        completedAtMillis: Long,
    ): CardsResult {
        require(task.deck.isNotEmpty()) { "Memorized deck cannot be empty" }

        val details = task.deck.mapIndexed { index, memorizedCard ->
            val recalledCard = answer.values.getOrNull(index)

            AnswerResult(
                id = index,
                memorizedCard = memorizedCard,
                recalledCard = recalledCard,
            )
        }

        return CardsResult(
            params = params,
            details = details,
            startedAtMillis = startedAtMillis,
            recallStartedAtMillis = recallStartedAtMillis,
            completedAtMillis = completedAtMillis
        )
    }
}