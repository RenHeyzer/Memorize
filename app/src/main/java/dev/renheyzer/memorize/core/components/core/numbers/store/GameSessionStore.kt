package dev.renheyzer.memorize.core.components.core.numbers.store

class GameSessionStore {

    var isRandom: Boolean = true
        private set

    var generatedNumbers: List<Int> = emptyList()
        private set

    var userAnswers: List<Int?> = emptyList()
        private set

    fun saveGeneratedNumbers(numbers: List<Int>, isRandom: Boolean) {
        generatedNumbers = numbers
        this.isRandom = isRandom
    }

    fun saveUserAnswers(answers: List<Int?>) {
        userAnswers = answers
    }

    fun clear() {
        generatedNumbers = emptyList()
        userAnswers = emptyList()
    }
}