package com.riahi.dogify.android

import android.app.Application
import com.riahi.dogify.di.initKoin
import org.koin.android.ext.koin.androidContext

class DogifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@DogifyApplication)
            modules(viewModelModule)
        }
    }
}