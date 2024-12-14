package com.example.transportistaapp

import android.app.Application
import com.mapbox.navigation.core.lifecycle.MapboxNavigationApp
import com.mapbox.navigation.base.options.NavigationOptions
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (!MapboxNavigationApp.isSetup()) {
            MapboxNavigationApp.setup {
                NavigationOptions.Builder(this).build()
            }
        }
    }
}
