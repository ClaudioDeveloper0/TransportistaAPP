package com.example.transportistaapp

import android.app.Application
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.transportistaapp.ui.homeTransportista.RoutesViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

}

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private val viewModel: RoutesViewModel by viewModels()
    // ...
}