package com.max_ro.avitokinopoisk.domain.models

data class MoviesEntity(
    val id: Int,
    val name: String,
    val year: Int,
    val poster: PosterEntity,
    val shortDescription: String?,
    val isSeries: String?,
    val movieRating: String?,
    val ageRating: String?,
    val genres: List<String>?,
    val countries: List<String>?,
    val networks: List<String>?,
    val page: Int = 1
)

data class PosterEntity(
    val url: String? = null,
    val previewUrl: String? = null
)
