package dev.renheyzer.memorize.feature.core.game.domain.model

@JvmInline
value class GameSessionId(val value: String)

data class GameSession(
    val id: GameSessionId,
    val type: GameType,
    val phase: GamePhase,
    val startedAtMillis: Long,
    val completedAtMillis: Long? = null,
)