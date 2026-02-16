package dev.renheyzer.memorize.core.common

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

data class AppDispatchers(
    val mainImmediate: CoroutineContext = Dispatchers.Main.immediate,
    val default: CoroutineContext = Dispatchers.Default,
    val io: CoroutineContext = Dispatchers.IO,
    val unconfined: CoroutineContext = Dispatchers.Unconfined
)