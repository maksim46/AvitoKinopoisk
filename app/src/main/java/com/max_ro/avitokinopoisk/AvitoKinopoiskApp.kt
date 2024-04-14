package com.max_ro.avitokinopoisk

import android.app.Application
import com.max_ro.avitokinopoisk.di.DaggerApplicationComponent

class AvitoKinopoiskApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

}