package dev.renheyzer.memorize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowSizeClass
import com.arkivanov.decompose.defaultComponentContext
import dev.renheyzer.memorize.components.root.DefaultRootComponent
import dev.renheyzer.memorize.core.utils.DeviceConfiguration
import dev.renheyzer.memorize.presentation.root.RootContent
import dev.renheyzer.memorize.ui.theme.MemorizeCorner
import dev.renheyzer.memorize.ui.theme.MemorizeSize
import dev.renheyzer.memorize.ui.theme.MemorizeStyle
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

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
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.statusBars,
                ) { innerPadding ->
                    val windowSizeClass: WindowSizeClass =
                        currentWindowAdaptiveInfo().windowSizeClass
                    val deviceConfiguration =
                        DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

                    when (deviceConfiguration) {
                        DeviceConfiguration.PHONE_PORTRAIT -> {
                            RootContent(
                                component = rootComponent,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            )
                        }

                        DeviceConfiguration.PHONE_LANDSCAPE -> {}
                        DeviceConfiguration.TABLET_PORTRAIT -> {}
                        DeviceConfiguration.TABLET_LANDSCAPE -> {}
                        else -> {}
                    }
                }
            }
        }
    }
}