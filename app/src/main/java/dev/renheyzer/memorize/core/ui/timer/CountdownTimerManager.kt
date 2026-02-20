package dev.renheyzer.memorize.core.ui.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Timer

class CountdownTimerManager(
    private val tickInterval: Long = 100L
) {

    private val _events = Channel<TimerEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private val _timeLeft = MutableStateFlow(0L)
    val timeLeft = _timeLeft.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()
    private var job: Job? = null
    private var startTime: Long = 0L
    private var initialDuration: Long = 0L

    fun setDuration(durationMillis: Long) {
        if (_isRunning.value) return

        initialDuration = durationMillis
        _timeLeft.value = durationMillis
    }

    fun start(scope: CoroutineScope) {
        if (_isRunning.value || _timeLeft.value <= 0) return

        _isRunning.value = true
        startTime = System.currentTimeMillis()

        val durationSnapshot = initialDuration

        job = scope.launch {
            while (isActive) {
                val currentTime = System.currentTimeMillis()
                val timeElapsedSinceStart = currentTime - startTime

                val newTimeLeft = durationSnapshot - timeElapsedSinceStart

                if (newTimeLeft <= 0) {
                    _timeLeft.value = 0L
                    _events.send(TimerEvent.Finished)
                    stop()
                    break
                } else {
                    _timeLeft.value = newTimeLeft
                }

                delay(tickInterval)
            }
        }
    }

    fun pause() {
        if (!_isRunning.value) return

        job?.cancel()
        _isRunning.value = false
        initialDuration = _timeLeft.value
    }

    fun stop() {
        job?.cancel()
        _isRunning.value = false
        _timeLeft.value = 0L
        initialDuration = 0L
    }

    fun reset(originalDuration: Long) {
        stop()
        setDuration(originalDuration)
    }

    sealed interface TimerEvent {
        data object Finished: TimerEvent
    }
}