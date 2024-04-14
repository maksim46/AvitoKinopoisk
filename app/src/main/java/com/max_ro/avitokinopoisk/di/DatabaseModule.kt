package com.max_ro.avitokinopoisk.di

import android.content.Context
import androidx.room.Room
import com.max_ro.avitokinopoisk.data.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideMoviesAppDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(context, MoviesDatabase::class.java, "movies.db").build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(db: MoviesDatabase) = db.getMoviesDao()
}

