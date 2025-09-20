package dev.renheyzer.memorize.core.utils

import androidx.window.core.layout.WindowSizeClass

enum class DeviceConfiguration {
    PHONE_PORTRAIT,
    PHONE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    DESKTOP;

    companion object {
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            val expandedWidth = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)
            val mediumWidth = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
            val compactWidth = !expandedWidth && !mediumWidth
            val expandedHeight = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_EXPANDED_LOWER_BOUND)
            val mediumHeight = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)
            val compactHeight = !expandedHeight && !mediumHeight

            return when {
                expandedWidth && mediumHeight -> TABLET_LANDSCAPE
                mediumWidth && expandedHeight -> TABLET_PORTRAIT
                expandedWidth && compactHeight -> PHONE_LANDSCAPE
                compactWidth && expandedHeight -> PHONE_PORTRAIT
                compactWidth && mediumHeight -> PHONE_PORTRAIT
                else -> DESKTOP
            }
        }
    }
}