package com.max_ro.avitokinopoisk.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.max_ro.avitokinopoisk.AvitoKinopoiskApp
import com.max_ro.avitokinopoisk.presentation.features.movies_main.ViewModelFactory
import com.max_ro.avitokinopoisk.presentation.theme.AvitoKinopoiskTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AvitoKinopoiskApp).component.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            AvitoKinopoiskTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MovieNavGraph { viewModelFactory }
                }
            }
        }
    }
}

