package dev.renheyzer.memorize.feature.core.numbers.domain.usecase

import dev.renheyzer.memorize.core.models.GameRandomConfig
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersMode
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersTask
import kotlin.random.Random

class GenerateNumbersUseCase {

    operator fun invoke(columns: Int, rows: Int, rounds: Int, mode: NumbersMode, randomConfig: GameRandomConfig? = null): NumbersTask {
        require(rounds > 0) {
            "Numbers quantity must be greater than zero"
        }
        require(columns > 0) {
            "Columns quantity must be greater than zero"
        }
        require(rows > 0) {
            "Rows quantity must be greater than zero"
        }

        val quantity = columns * rows * rounds

        val randomConfig = randomConfig
            ?: GameRandomConfig(
                seed = Random.nextLong(),
                algorithmVersion = 1
            )
        val random = Random(randomConfig.seed)

        when (randomConfig.algorithmVersion) {
            1 -> {
                val numbers = when (mode) {
                    NumbersMode.BINARY -> List(quantity) { if (random.nextBoolean()) 1 else 0 }
                    NumbersMode.RANDOM -> List(quantity) { random.nextInt(1, 101) }
                }
                return NumbersTask(numbers, randomConfig)
            }
            else -> error("Unsupported numbers generation version")
        }
    }
}