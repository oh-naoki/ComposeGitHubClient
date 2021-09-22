package com.example.android.composegithubclient

import android.app.Application
import com.example.android.composegithubclient.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApp)
            modules(networkModule)
        }
    }

}