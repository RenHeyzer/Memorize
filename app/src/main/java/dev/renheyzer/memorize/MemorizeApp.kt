package dev.renheyzer.memorize

import android.app.Application
import dev.renheyzer.memorize.core.di.AppDependencies
import dev.renheyzer.memorize.core.di.AppDependenciesImpl

class MemorizeApp : Application() {

    lateinit var appDependencies: AppDependencies

    override fun onCreate() {
        super.onCreate()
        appDependencies = AppDependenciesImpl(applicationContext)
    }
}