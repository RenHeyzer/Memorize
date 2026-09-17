package dev.renheyzer.memorize.feature.core.statistics.domain.usecase

class DetermineBestGameScoreUseCase {

    operator fun invoke(correctCount: Int, totalCount: Int, accuracy: Float, duration: Long) {
        when {
            correctCount <= 6 && totalCount <= 6 -> (accuracy * 100f)
            correctCount in 7..12 && totalCount in 7..12 -> (accuracy * 100f) + 0.5f
            correctCount in 13..18 && totalCount in 13..18 -> (accuracy * 100f) + 1.0f
            correctCount in 19..24 && totalCount in 19..24 -> (accuracy * 100f) + 1.5f
            correctCount in 25..30 && totalCount in 25..30 -> (accuracy * 100f) + 2.0f
            correctCount in 31..36 && totalCount in 31..36 -> (accuracy * 100f) + 2.5f
            correctCount in 37..42 && totalCount in 37..42 -> (accuracy * 100f) + 3.0f
            correctCount in 43..48 && totalCount in 43..48 -> (accuracy * 100f) + 3.5f
            correctCount in 49..54 && totalCount in 49..54 -> (accuracy * 100f) + 4.0f
            correctCount in 55..60 && totalCount in 55..60 -> (accuracy * 100f) + 4.5f
            correctCount in 61..66 && totalCount in 61..66 -> (accuracy * 100f) + 5.0f
            correctCount in 67..72 && totalCount in 67..72 -> (accuracy * 100f) + 5.5f
            correctCount in 73..78 && totalCount in 73..78 -> (accuracy * 100f) + 6.0f
            correctCount in 79..84 && totalCount in 79..84 -> (accuracy * 100f) + 6.5f
            correctCount in 85..90 && totalCount in 85..90 -> (accuracy * 100f) + 7.0f
            correctCount in 91..96 && totalCount in 91..96 -> (accuracy * 100f) + 7.5f

        }
    }
}