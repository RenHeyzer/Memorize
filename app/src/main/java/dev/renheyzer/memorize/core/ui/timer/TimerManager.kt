package dev.renheyzer.memorize.core.ui.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TimerManager(
    private val tickInterval: Long = 100L
) {

    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime = _elapsedTime.asStateFlow()

    private var job: Job? = null
    private var startTime: Long = 0L
    private var accumulatedTime: Long = 0L
    private var isRunning = false

    fun start(scope: CoroutineScope) {
        if (isRunning) return

        isRunning = true
        startTime = System.currentTimeMillis()

        job = scope.launch {
            while (isActive) {
                val currentTime = System.currentTimeMillis()
                _elapsedTime.value = (currentTime - startTime) + accumulatedTime
                delay(tickInterval)
            }
        }
    }

    fun pause() {
        if (!isRunning) return

        job?.cancel()
        isRunning = false
        accumulatedTime = _elapsedTime.value
    }

    fun stop() {
        job?.cancel()
        isRunning = false
        accumulatedTime = 0L
        _elapsedTime.value = 0L
    }
}