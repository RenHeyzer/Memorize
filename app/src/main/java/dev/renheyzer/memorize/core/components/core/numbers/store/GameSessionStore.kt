package dev.renheyzer.memorize.core.components.core.numbers.store

class GameSessionStore {
    var generatedNumbers: List<Int> = emptyList()
        private set

    var userAnswers: List<Int> = emptyList()
        private set

    fun saveGeneratedNumbers(numbers: List<Int>) {
        generatedNumbers = numbers
    }

    fun saveUserAnswers(answers: List<Int>) {
        userAnswers = answers
    }

    fun clear() {
        generatedNumbers = emptyList()
        userAnswers = emptyList()
    }
}