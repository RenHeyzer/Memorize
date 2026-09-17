package dev.renheyzer.memorize.core.ui.decompose

import dev.renheyzer.memorize.core.ui.SnackbarController
import dev.renheyzer.memorize.core.ui.StringResolver
import kotlin.coroutines.CoroutineContext

data class ComponentEnvironment(
    val mainContext: CoroutineContext,
    val stringResolver: StringResolver,
    val snackbarController: SnackbarController
)