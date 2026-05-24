package dev.renheyzer.memorize.core.di.app

import dev.renheyzer.memorize.core.common.AppDispatchers
import dev.renheyzer.memorize.core.ui.SnackbarController
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.core.ui.timer.TimerManager
import dev.renheyzer.memorize.feature.auth.di.AuthDependencies
import dev.renheyzer.memorize.feature.core.numbers.di.NumbersDependencies

interface AppDependencies {
    val dispatchers: AppDispatchers
    val snackbarController: SnackbarController

    val componentEnvironment: ComponentEnvironment

    fun authDependencies(): AuthDependencies
    fun numbersDependencies(): NumbersDependencies

    val timerManager: TimerManager
    val countdownTimerManager: CountdownTimerManager
}