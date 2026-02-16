package dev.renheyzer.memorize

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
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.arkivanov.decompose.defaultComponentContext
import dev.renheyzer.memorize.core.components.root.DefaultRootComponent
import dev.renheyzer.memorize.core.utils.DeviceConfiguration
import dev.renheyzer.memorize.core.ui.LocalSnackbarController
import dev.renheyzer.memorize.feature.root.ui.root.RootContent
import dev.renheyzer.memorize.ui.theme.MemorizeCorner
import dev.renheyzer.memorize.ui.theme.MemorizeSize
import dev.renheyzer.memorize.ui.theme.MemorizeStyle
import dev.renheyzer.memorize.ui.theme.MemorizeTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appDependencies = (application as MemorizeApp).appDependencies

        enableEdgeToEdge()
        val rootComponent = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            appDependencies = appDependencies
        )
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

                CompositionLocalProvider(LocalSnackbarController provides appDependencies.snackbarController) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = WindowInsets.statusBars,
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState)
                        }
                    ) { innerPadding ->
                        val windowSizeClass: WindowSizeClass =
                            currentWindowAdaptiveInfo().windowSizeClass
                        val deviceConfiguration =
                            DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

                        RootContent(
                            component = rootComponent,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                        )

                        when (deviceConfiguration) {
                            DeviceConfiguration.PHONE_PORTRAIT -> {
                                RootContent(
                                    component = rootComponent,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                )
                            }

                            DeviceConfiguration.PHONE_LANDSCAPE -> {}
                            DeviceConfiguration.TABLET_PORTRAIT -> {}
                            DeviceConfiguration.TABLET_LANDSCAPE -> {}
                            DeviceConfiguration.DESKTOP -> {
                                Log.d("Root", "true")

                            }
                        }
                    }
                }
            }
        }
    }
}