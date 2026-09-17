package dev.renheyzer.memorize.feature.core.numbers.data.source.remote

import dev.renheyzer.memorize.feature.core.numbers.data.dto.NumbersResultDto

interface NumbersRemoteDataSource {

    val userId: String

    suspend fun saveGameResults(result: NumbersResultDto)
    suspend fun fetchGameResults(): List<NumbersResultDto>
}