package dev.renheyzer.memorize

import android.app.Application
import dev.renheyzer.memorize.di.AppDependencies

class MemorizeApp : Application() {

    val appDependencies by lazy { AppDependencies() }

    override fun onCreate() {
        super.onCreate()
    }
}