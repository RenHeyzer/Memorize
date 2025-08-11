package dev.renheyzer.memorize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import dev.renheyzer.memorize.components.root.DefaultRootComponent
import dev.renheyzer.memorize.presentation.root.RootContent
import dev.renheyzer.memorize.ui.theme.MemorizeCorner
import dev.renheyzer.memorize.ui.theme.MemorizeSize
import dev.renheyzer.memorize.ui.theme.MemorizeStyle
import dev.renheyzer.memorize.ui.theme.MemorizeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val rootComponent = DefaultRootComponent(componentContext = defaultComponentContext())
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RootContent(
                        component = rootComponent,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}