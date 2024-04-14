package com.max_ro.avitokinopoisk.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.max_ro.avitokinopoisk.data.database.models.MoviesDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query(
        """
    SELECT * FROM movies
    WHERE (year IS NULL OR CAST(year AS INTEGER) BETWEEN :startYear AND :endYear)
      AND (:isSeries IS NULL OR isSeries = :isSeries)
      AND (movieRating IS NULL OR movieRating BETWEEN :movieRatingStart AND :movieRatingEnd)
     AND (ageRating IS NULL OR CAST(ageRating AS INTEGER) BETWEEN :startAgeRating AND :endAgeRating)
      AND (:genres IS NULL OR genres LIKE '%' || :genres || '%')
      AND (:countries IS NULL OR countries LIKE '%' || :countries || '%')
      AND (:networks IS NULL OR networks LIKE '%' || :networks || '%')
"""
    )
    fun getAllMovie(
        startYear: Int?,
        endYear: Int?,
        isSeries: String?,
        movieRatingStart: Double?,
        movieRatingEnd: Double?,
        startAgeRating: Int?,
        endAgeRating: Int?,
        genres: String?,
        countries: String?,
        networks: String?
    ): PagingSource<Int, MoviesDBO>


    @Query("SELECT * FROM movies WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchMoviesByName(searchQuery: String): PagingSource<Int, MoviesDBO>

    @Query("SELECT COUNT(*) FROM movies WHERE name LIKE '%' || :searchQuery || '%'")
    suspend fun countMoviesByName(searchQuery: String): Int

    @Query("SELECT*FROM movies WHERE id==:movieId LIMIT 1")
    fun getOneMovie(movieId: String): Flow<MoviesDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<MoviesDBO>)

    @Delete
    suspend fun delete(movies: List<MoviesDBO>)

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}
