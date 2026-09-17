package dev.renheyzer.memorize.feature.core.numbers.data.dto

import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersMode
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersParam
import kotlinx.serialization.Serializable

@Serializable
data class NumbersParamDto(
    val rounds: Int = 0,
    val columns: Int = 0,
    val rows: Int = 0,
    val mode: NumbersMode = NumbersMode.RANDOM,
    val memorizationTimeSeconds: Int = 0,
    val recallTimeSeconds: Int? = null
)

fun NumbersParamDto.toDomain(): NumbersParam {
    return NumbersParam(
        rounds = rounds,
        columns = columns,
        rows = rows,
        mode = mode,
        memorizationTimeSeconds = memorizationTimeSeconds,
        recallTimeSeconds = recallTimeSeconds
    )
}

fun NumbersParam.toDto(): NumbersParamDto {
    return NumbersParamDto(
        rounds = rounds,
        columns = columns,
        rows = rows,
        mode = mode,
        memorizationTimeSeconds = memorizationTimeSeconds,
        recallTimeSeconds = recallTimeSeconds
    )
}