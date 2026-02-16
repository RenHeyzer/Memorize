package dev.renheyzer.memorize.core.di

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dev.renheyzer.memorize.core.common.AppDispatchers
import dev.renheyzer.memorize.core.ui.SnackbarController
import dev.renheyzer.memorize.core.ui.StringResolver
import dev.renheyzer.memorize.core.utils.DefaultStringResolver

class AppDependenciesImpl(context: Context) : AppDependencies {

    override val dispatchers: AppDispatchers = AppDispatchers()
    override val snackbarController by lazy { SnackbarController() }
    override val stringResolver: StringResolver by lazy { DefaultStringResolver(context) }

    private val firebaseAuth by lazy { Firebase.auth }
    private val firebaseFirestore by lazy { Firebase.firestore }

    override fun authDependencies(): AuthDependencies = AuthDependenciesImpl(firebaseAuth)
}