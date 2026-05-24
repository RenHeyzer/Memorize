package dev.renheyzer.memorize.feature.core.game.domain.model

data class GameResult(
    val sessionId: GameSessionId,
    val type: GameType,
    val correctCount: Int,
    val totalCount: Int,
    val score: Int,
    val startedAtMillis: Long,
    val completedAtMillis: Long,
)