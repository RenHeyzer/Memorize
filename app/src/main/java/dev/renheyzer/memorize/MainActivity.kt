package dev.renheyzer.memorize

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.arkivanov.decompose.retainedComponent
import dev.renheyzer.memorize.core.components.root.DefaultRootComponent
import dev.renheyzer.memorize.core.components.root.RootComponent
import dev.renheyzer.memorize.core.di.factory.ComponentFactory
import dev.renheyzer.memorize.core.ui.LocalSnackbarController
import dev.renheyzer.memorize.feature.root.ui.root.RootContent
import dev.renheyzer.memorize.ui.theme.MemorizeCorner
import dev.renheyzer.memorize.ui.theme.MemorizeSize
import dev.renheyzer.memorize.ui.theme.MemorizeStyle
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private var rootComponent: RootComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appDependencies = (application as MemorizeApp).appDependencies
        val componentFactory = ComponentFactory(appDependencies)

        enableEdgeToEdge()
        val root = retainedComponent { componentContext ->
            DefaultRootComponent(
                componentContext = componentContext,
                appDependencies = appDependencies,
                factory = componentFactory
            )
        }
        rootComponent = root

        setContent {
            val isDarkModeValue = isSystemInDarkTheme()
            val memorizeStyle = remember {
                mutableStateOf(
                    MemorizeStyle(
                        textSize = MemorizeSize.Medium,
                        corner = MemorizeCorner.Medium,
                        isDarkMode = isDarkModeValue
                    )
                )
            }
            MemorizeTheme(
                textSize = memorizeStyle.value.textSize,
                darkTheme = memorizeStyle.value.isDarkMode
            ) {
                val snackbarHostState = remember { SnackbarHostState() }

                val scope = rememberCoroutineScope()
                val lifecycleOwner = LocalLifecycleOwner.current
                LaunchedEffect(
                    lifecycleOwner.lifecycle,
                    appDependencies.snackbarController.events
                ) {
                    lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        withContext(appDependencies.dispatchers.mainImmediate) {
                            appDependencies.snackbarController.events.collect { event ->
                                scope.launch {
                                    snackbarHostState.currentSnackbarData?.dismiss()

                                    val result = snackbarHostState.showSnackbar(
                                        message = event.message,
                                        actionLabel = event.action?.name,
                                        duration = SnackbarDuration.Long
                                    )

                                    if (result == SnackbarResult.ActionPerformed) {
                                        event.action?.action?.invoke()
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                    }
                                }
                            }
                        }
                    }
                }

                CompositionLocalProvider(
                    LocalSnackbarController provides appDependencies.snackbarController,
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = WindowInsets.statusBars,
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState)
                        },
                    ) { innerPadding ->
                        RootContent(
                            component = root,
                            modifier = Modifier
                                .padding(innerPadding),
                        )
                    }
                }
            }
        }

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val data: Uri? = intent?.data
        if (data != null) {
            Log.d("Root", "handleIntent: $data")
            rootComponent?.handleDeepLink(data)
        }
    }
}