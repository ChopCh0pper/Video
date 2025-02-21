package com.example.vk_task

import android.app.Application
import com.example.vk_task.di.appModule
import com.example.vk_task.di.dataModule
import com.example.vk_task.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(networkModule, dataModule, appModule)
        }
    }
}