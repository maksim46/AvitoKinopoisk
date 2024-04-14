package com.max_ro.avitokinopoisk.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.max_ro.avitokinopoisk.data.database.Converters.Converters
import com.max_ro.avitokinopoisk.data.database.dao.MoviesDao
import com.max_ro.avitokinopoisk.data.database.models.MoviesDBO

@Database(entities = [MoviesDBO::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}


