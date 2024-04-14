package com.max_ro.avitokinopoisk.di

import android.content.Context
import com.max_ro.avitokinopoisk.AvitoKinopoiskApp
import com.max_ro.avitokinopoisk.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, NetworkModule::class, ViewModelModule::class, CommonModule::class])
interface ApplicationComponent {

    fun inject(application: AvitoKinopoiskApp)
    fun inject(activity: MainActivity)


    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}