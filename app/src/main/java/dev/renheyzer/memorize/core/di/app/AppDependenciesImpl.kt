package dev.renheyzer.memorize.core.di.app

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dev.renheyzer.memorize.core.common.AppDispatchers
import dev.renheyzer.memorize.core.components.auth.dependencies.AuthDependencies
import dev.renheyzer.memorize.core.components.auth.dependencies.AuthDependenciesImpl
import dev.renheyzer.memorize.core.components.core.numbers.dependencies.NumbersDependencies
import dev.renheyzer.memorize.core.components.core.numbers.dependencies.NumbersDependenciesImpl
import dev.renheyzer.memorize.core.ui.SnackbarController
import dev.renheyzer.memorize.core.ui.timer.CountdownTimerManager
import dev.renheyzer.memorize.core.ui.timer.TimerManager
import dev.renheyzer.memorize.core.ui.DefaultStringResolver
import dev.renheyzer.memorize.core.ui.decompose.ComponentEnvironment

class AppDependenciesImpl(context: Context) : AppDependencies {

    override val dispatchers: AppDispatchers = AppDispatchers()
    override val snackbarController: SnackbarController = SnackbarController()

    override val componentEnvironment: ComponentEnvironment by lazy {
        ComponentEnvironment(
            mainContext = dispatchers.mainImmediate,
            stringResolver = DefaultStringResolver(context),
            snackbarController = snackbarController,
        )
    }

    private val firebaseAuth by lazy { Firebase.auth }
    private val firebaseFirestore by lazy { Firebase.firestore }

    override fun authDependencies(): AuthDependencies = AuthDependenciesImpl(firebaseAuth)
    override fun numbersDependencies(): NumbersDependencies = NumbersDependenciesImpl()


    override val timerManager: TimerManager by lazy { TimerManager() }
    override val countdownTimerManager: CountdownTimerManager by lazy { CountdownTimerManager() }
}