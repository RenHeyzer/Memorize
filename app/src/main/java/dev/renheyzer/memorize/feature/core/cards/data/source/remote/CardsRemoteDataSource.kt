package dev.renheyzer.memorize.feature.core.cards.data.source.remote

import dev.renheyzer.memorize.feature.core.cards.data.dto.CardsResultDto

interface CardsRemoteDataSource {
    val userId: String

    suspend fun saveGameResult(result: CardsResultDto)
    suspend fun fetchGameResults(): List<CardsResultDto>
}
