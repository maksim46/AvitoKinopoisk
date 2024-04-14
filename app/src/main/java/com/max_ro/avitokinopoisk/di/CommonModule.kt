package com.max_ro.avitokinopoisk.di

import android.content.Context
import com.max_ro.avitokinopoisk.domain.common.AndroidLogcatLogger
import com.max_ro.avitokinopoisk.domain.common.LastFiveIntChecker
import com.max_ro.avitokinopoisk.domain.common.Logger
import com.max_ro.avitokinopoisk.data.dataStoreRecentReq.ResentReqDataStoreManager
import dagger.Module
import dagger.Provides

@Module
class CommonModule {

    @Provides
    fun provideLogger(): Logger {
        return AndroidLogcatLogger()
    }

    @Provides
    fun provideDataStoreManager(context: Context): ResentReqDataStoreManager {
        return ResentReqDataStoreManager(context)
    }

    @Provides
    fun provideLastItemTrackerManager(): LastFiveIntChecker {
        return LastFiveIntChecker()
    }

}

