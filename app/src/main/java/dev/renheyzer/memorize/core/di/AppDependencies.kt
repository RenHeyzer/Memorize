package dev.renheyzer.memorize.core.di

import dev.renheyzer.memorize.core.common.AppDispatchers
import dev.renheyzer.memorize.core.ui.SnackbarController
import dev.renheyzer.memorize.core.ui.StringResolver

interface AppDependencies {
    val dispatchers: AppDispatchers
    val snackbarController: SnackbarController
    val stringResolver: StringResolver

    fun authDependencies(): AuthDependencies
}