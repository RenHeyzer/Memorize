package dev.renheyzer.memorize.feature.core.numbers.domain.usecase

import kotlin.random.Random

class GenerateNumbersUseCase {

    operator fun invoke(quantity: Int, isRandom: Boolean = true): List<Int> {
        if (quantity <= 0) return emptyList()

        val numbers = if (isRandom) {
            List(quantity) { Random.nextInt(1, 101) }
        } else {
            List(quantity) { if (Random.nextBoolean()) 1 else 0 }
        }
        return numbers
    }
}