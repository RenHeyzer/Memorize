package dev.renheyzer.memorize.feature.core.cards

import dev.renheyzer.memorize.core.common.Either
import dev.renheyzer.memorize.core.common.NetworkError
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsResult

interface CardsRepository {

    suspend fun saveGameResult(result: CardsResult): Either<NetworkError, Unit>
}