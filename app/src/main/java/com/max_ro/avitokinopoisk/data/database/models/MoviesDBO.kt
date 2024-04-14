package com.max_ro.avitokinopoisk.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MoviesDBO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("year") val year: Int,
    @Embedded("poster") val poster: PosterDBO,
    @ColumnInfo("shortDescription") val shortDescription: String?,
    @ColumnInfo("isSeries") val isSeries: String?,
    @ColumnInfo("movieRating") val movieRating: Double?,
    @ColumnInfo("ageRating") val ageRating: Int,
    @ColumnInfo("genres") val genres: List<String>?,
    @ColumnInfo("countries") val countries: List<String>?,
    @ColumnInfo("networks") val networks: List<String>?,
    @ColumnInfo("page") val page: Int = 1,
    @Embedded("posters") val posters: PostersDBO?,
    @Embedded("review") val reviewDTO: ReviewDBO?,

    )


data class PosterDBO(
    @ColumnInfo("url") val url: String?,
    @ColumnInfo("previewUrl") val previewUrl: String?
)


data class ReviewDBO(
    @ColumnInfo("movieId") val movieId: String,
    @ColumnInfo("review") val review: String,
    @ColumnInfo("author") val author: String,
    @ColumnInfo("type") val type: String,
)

data class PostersDBO(
    @ColumnInfo("movieId") val movieId: String,
    @ColumnInfo("poster") val poster: String,
)

