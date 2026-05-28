package dev.renheyzer.memorize.core.common

sealed class Either<out L, out R> {
    data class Left<out L>(val value: L) : Either<L, Nothing>()
    data class Right<out R>(val value: R) : Either<Nothing, R>()

    val isLeft: Boolean get() = this is Left
    val isRight: Boolean get() = this is Right
}

inline fun <L, R, T> Either<L, R>.fold(
    onLeft: (L) -> T,
    onRight: (R) -> T
): T {
    return when (this) {
        is Either.Left -> onLeft(value)
        is Either.Right -> onRight(value)
    }
}

inline fun <L, R, T> Either<L, R>.map(transform: (R) -> T): Either<L, T> {
    return when (this) {
        is Either.Left -> this
        is Either.Right -> Either.Right(transform(value))
    }
}

inline fun <L, R, T> Either<L, R>.flatMap(transform: (R) -> Either<L, T>): Either<L, T> {
    return when (this) {
        is Either.Left -> this
        is Either.Right -> transform(this.value)
    }
}