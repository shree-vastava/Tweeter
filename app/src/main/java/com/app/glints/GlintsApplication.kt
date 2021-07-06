package com.app.glints

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class GlintsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}