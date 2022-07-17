package com.riahi.dogify.android

import android.app.Application
import com.riahi.dogify.di.initKoin

class DogifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}