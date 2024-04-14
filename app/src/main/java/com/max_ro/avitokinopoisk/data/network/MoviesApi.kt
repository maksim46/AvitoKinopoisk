package com.max_ro.avitokinopoisk.data.network

import FullMoviesDTO
import androidx.annotation.IntRange
import com.max_ro.avitokinopoisk.data.network.Constant.Companion.DEFAULT_PAGE_LIMIT
import com.max_ro.avitokinopoisk.data.network.Constant.Companion.NOT_NULL_FIELDS
import com.max_ro.avitokinopoisk.data.network.Constant.Companion.POSTER_NOT_NULL_FIELDS
import com.max_ro.avitokinopoisk.data.network.Constant.Companion.POSTER_SELECTED_FIELDS
import com.max_ro.avitokinopoisk.data.network.Constant.Companion.REVIEW_NOT_NULL_FIELDS
import com.max_ro.avitokinopoisk.data.network.Constant.Companion.REVIEW_SELECTED_FIELDS
import com.max_ro.avitokinopoisk.data.network.Constant.Companion.SELECTED_FIELDS
import com.max_ro.avitokinopoisk.data.network.models.MoviesDTO
import com.max_ro.avitokinopoisk.data.network.models.PostersDTO
import com.max_ro.avitokinopoisk.data.network.models.ResponseDTO
import com.max_ro.avitokinopoisk.data.network.models.ReviewDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//[API documentation] (https://api.kinopoisk.dev/documentation#/)

interface MoviesApi {
    @GET("movie")
    suspend fun getMovies(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 100) limit: Int? = DEFAULT_PAGE_LIMIT,
        @Query("selectFields") selectFields: List<String> = SELECTED_FIELDS,
        @Query("notNullFields") notNullFields: List<String> = NOT_NULL_FIELDS,
        @Query("isSeries") isSeries: String?,
        @Query("year") year: String?,
        @Query("rating.kp") movieRating: String?,
        @Query("ageRating") ageRating: String?,
        @Query("genres.name") genres: List<String>?,
        @Query("countries.name") countries: List<String>?,
        @Query("networks.items.name") networks: List<String>?,
    ): Result<ResponseDTO<MoviesDTO>>


    @GET("movie/search")
    suspend fun searchMovies(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1, to = 100) limit: Int? = null,
        @Query("query") query: String?,
    ): Result<ResponseDTO<FullMoviesDTO>>


    @GET("review")
    suspend fun getReview(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1) limit: Int? = DEFAULT_PAGE_LIMIT,
        @Query("selectFields") selectFields: List<String> = REVIEW_SELECTED_FIELDS,
        @Query("notNullFields") notNullFields: List<String> = REVIEW_NOT_NULL_FIELDS,
        @Query("movieId") movieId: String?,
    ): Response<ResponseDTO<ReviewDTO>>


    @GET("image")
    suspend fun getPosters(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("limit") @IntRange(from = 1) limit: Int? = DEFAULT_PAGE_LIMIT,
        @Query("selectFields") selectFields: List<String> = POSTER_SELECTED_FIELDS,
        @Query("notNullFields") notNullFields: List<String> = POSTER_NOT_NULL_FIELDS,
        @Query("movieId") movieId: String?,
    ): Response<ResponseDTO<PostersDTO>>
}



