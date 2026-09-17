package dev.renheyzer.memorize.feature.core.numbers.presentation.ui.result

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import dev.renheyzer.memorize.feature.core.cards.domain.model.Card
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardRank
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardSuit
import dev.renheyzer.memorize.feature.core.cards.domain.model.CardsAnswer
import dev.renheyzer.memorize.feature.core.cards.domain.usecase.GenerateOrderedDeckUseCase
import dev.renheyzer.memorize.feature.core.cards.presentation.store.recall.RecallUiState
import dev.renheyzer.memorize.feature.core.numbers.domain.model.AnswerResult
import dev.renheyzer.memorize.feature.core.numbers.presentation.component.result.ResultComponent
import dev.renheyzer.memorize.feature.core.numbers.presentation.model.RoundResultUi
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultIntent
import dev.renheyzer.memorize.feature.core.numbers.presentation.store.result.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeResultComponent : ResultComponent {

    val mockAnswerResults = listOf(
        AnswerResult(
            index = 0,
            number = 42,
            answer = 42
        ), // Correct
        AnswerResult(
            index = 1,
            number = 15,
            answer = 10
        ), // Incorrect
        AnswerResult(
            index = 2,
            number = 7,
            answer = null
        ), // Unanswered
        AnswerResult(
            index = 3,
            number = 99,
            answer = 99
        ), // Correct
        AnswerResult(
            index = 4,
            number = 12,
            answer = 21
        ), // Incorrect
        AnswerResult(
            index = 5,
            number = 23,
            answer = 23
        ), // Correct
        AnswerResult(
            index = 6,
            number = 8,
            answer = null
        ), // Unanswered
        AnswerResult(
            index = 7,
            number = 55,
            answer = 55
        ), // Correct
        AnswerResult(
            index = 8,
            number = 100,
            answer = 101
        )  // Incorrect
    )

    override val uiState: StateFlow<ResultState> = MutableStateFlow(
        ResultState(
            rounds = 10,
            roundsResult = listOf(
                RoundResultUi(
                    id = "rnd_01",
                    details = mockAnswerResults,
                    totalCount = 10,
                    correctCount = 10,
                    scorePercentage = "100%",
                    memorizeTime = "00:30",
                    recallTime = "00:45",
                    totalSpentTime = "01:15"
                ),
                RoundResultUi(
                    id = "rnd_02",
                    details = mockAnswerResults,
                    totalCount = 10,
                    correctCount = 8,
                    scorePercentage = "80%",
                    memorizeTime = "00:45",
                    recallTime = "01:00",
                    totalSpentTime = "01:45"
                ),
                RoundResultUi(
                    id = "rnd_03",
                    details = mockAnswerResults,
                    totalCount = 5,
                    correctCount = 3,
                    scorePercentage = "60%",
                    memorizeTime = "00:20",
                    recallTime = "00:40",
                    totalSpentTime = "01:00"
                ),
                RoundResultUi(
                    id = "rnd_04",
                    details = mockAnswerResults,
                    totalCount = 20,
                    correctCount = 15,
                    scorePercentage = "75%",
                    memorizeTime = "01:00",
                    recallTime = "02:10",
                    totalSpentTime = "03:10"
                ),
                RoundResultUi(
                    id = "rnd_05",
                    details = mockAnswerResults,
                    totalCount = 10,
                    correctCount = 5,
                    scorePercentage = "50%",
                    memorizeTime = "00:30",
                    recallTime = "00:50",
                    totalSpentTime = "01:20"
                ),
                RoundResultUi(
                    id = "rnd_06",
                    details = mockAnswerResults,
                    totalCount = 15,
                    correctCount = 14,
                    scorePercentage = "93.3%",
                    memorizeTime = "00:40",
                    recallTime = "01:10",
                    totalSpentTime = "01:50"
                ),
                RoundResultUi(
                    id = "rnd_07",
                    details = mockAnswerResults,
                    totalCount = 8,
                    correctCount = 4,
                    scorePercentage = "50%",
                    memorizeTime = "00:25",
                    recallTime = "00:35",
                    totalSpentTime = "01:00"
                ),
                RoundResultUi(
                    id = "rnd_08",
                    details = mockAnswerResults,
                    totalCount = 12,
                    correctCount = 12,
                    scorePercentage = "100%",
                    memorizeTime = "00:35",
                    recallTime = "00:55",
                    totalSpentTime = "01:30"
                ),
                RoundResultUi(
                    id = "rnd_09",
                    details = mockAnswerResults,
                    totalCount = 10,
                    correctCount = 7,
                    scorePercentage = "70%",
                    memorizeTime = "00:50",
                    recallTime = "01:15",
                    totalSpentTime = "02:05"
                ),
                RoundResultUi(
                    id = "rnd_10",
                    details = mockAnswerResults,
                    totalCount = 15,
                    correctCount = 11,
                    scorePercentage = "73.3%",
                    memorizeTime = "00:45",
                    recallTime = "01:30",
                    totalSpentTime = "02:15"
                )
            ),
            correctCount = 23,
            totalCount = 32,
            scorePercentage = "80.32%"
        )
    )

    override fun onIntent(intent: ResultIntent) = Unit
}

class ResultStateProvider : PreviewParameterProvider<ResultState> {
    val mockAnswerResults = listOf(
        AnswerResult(
            index = 0,
            number = 42,
            answer = 42
        ), // Correct
        AnswerResult(
            index = 1,
            number = 15,
            answer = 10
        ), // Incorrect
        AnswerResult(
            index = 2,
            number = 7,
            answer = null
        ), // Unanswered
        AnswerResult(
            index = 3,
            number = 99,
            answer = 99
        ), // Correct
        AnswerResult(
            index = 4,
            number = 12,
            answer = 21
        ), // Incorrect
        AnswerResult(
            index = 5,
            number = 23,
            answer = 23
        ), // Correct
        AnswerResult(
            index = 6,
            number = 8,
            answer = null
        ), // Unanswered
        AnswerResult(
            index = 7,
            number = 55,
            answer = 55
        ), // Correct
        AnswerResult(
            index = 8,
            number = 100,
            answer = 101
        )  // Incorrect
    )

    override val values = sequenceOf(
        ResultState(
            rounds = 10,
            roundsResult = listOf(
                RoundResultUi(
                    id = "rnd_01",
                    details = mockAnswerResults,
                    totalCount = 10,
                    correctCount = 10,
                    scorePercentage = "100%",
                    memorizeTime = "00:30",
                    recallTime = "00:45",
                    totalSpentTime = "01:15"
                ),
                RoundResultUi(
                    id = "rnd_02",
                    details = mockAnswerResults,
                    totalCount = 10,
                    correctCount = 8,
                    scorePercentage = "80%",
                    memorizeTime = "00:45",
                    recallTime = "01:00",
                    totalSpentTime = "01:45"
                ),
                RoundResultUi(
                    id = "rnd_03",
                    details = mockAnswerResults,
                    totalCount = 5,
                    correctCount = 3,
                    scorePercentage = "60%",
                    memorizeTime = "00:20",
                    recallTime = "00:40",
                    totalSpentTime = "01:00"
                ),
                RoundResultUi(
                    id = "rnd_04",
                    details = mockAnswerResults,
                    totalCount = 20,
                    correctCount = 15,
                    scorePercentage = "75%",
                    memorizeTime = "01:00",
                    recallTime = "02:10",
                    totalSpentTime = "03:10"
                ),
                RoundResultUi(
                    id = "rnd_05",
                    details = mockAnswerResults,
                    totalCount = 10,
                    correctCount = 5,
                    scorePercentage = "50%",
                    memorizeTime = "00:30",
                    recallTime = "00:50",
                    totalSpentTime = "01:20"
                ),
                RoundResultUi(
                    id = "rnd_06",
                    details = mockAnswerResults,
                    totalCount = 15,
                    correctCount = 14,
                    scorePercentage = "93.3%",
                    memorizeTime = "00:40",
                    recallTime = "01:10",
                    totalSpentTime = "01:50"
                ),
                RoundResultUi(
                    id = "rnd_07",
                    details = mockAnswerResults,
                    totalCount = 8,
                    correctCount = 4,
                    scorePercentage = "50%",
                    memorizeTime = "00:25",
                    recallTime = "00:35",
                    totalSpentTime = "01:00"
                ),
                RoundResultUi(
                    id = "rnd_08",
                    details = mockAnswerResults,
                    totalCount = 12,
                    correctCount = 12,
                    scorePercentage = "100%",
                    memorizeTime = "00:35",
                    recallTime = "00:55",
                    totalSpentTime = "01:30"
                ),
                RoundResultUi(
                    id = "rnd_09",
                    details = mockAnswerResults,
                    totalCount = 10,
                    correctCount = 7,
                    scorePercentage = "70%",
                    memorizeTime = "00:50",
                    recallTime = "01:15",
                    totalSpentTime = "02:05"
                ),
                RoundResultUi(
                    id = "rnd_10",
                    details = mockAnswerResults,
                    totalCount = 15,
                    correctCount = 11,
                    scorePercentage = "73.3%",
                    memorizeTime = "00:45",
                    recallTime = "01:30",
                    totalSpentTime = "02:15"
                )
            ),
            correctCount = 23,
            totalCount = 32,
            scorePercentage = "80.32%"
        )
    )
}