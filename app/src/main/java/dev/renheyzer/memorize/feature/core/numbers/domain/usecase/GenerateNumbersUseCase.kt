package dev.renheyzer.memorize.feature.core.numbers.domain.usecase

import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersMode
import dev.renheyzer.memorize.feature.core.numbers.domain.model.NumbersTask
import kotlin.random.Random

class GenerateNumbersUseCase {

    operator fun invoke(quantity: Int, mode: NumbersMode): NumbersTask {
        require(quantity > 0) {
            "Numbers quantity must be greater than zero"
        }

        val numbers = when (mode) {
            NumbersMode.BINARY -> List(quantity) { if (Random.nextBoolean()) 1 else 0 }
            NumbersMode.RANDOM -> List(quantity) { Random.nextInt(1, 101) }
        }
        return NumbersTask(numbers)
    }
}