package com.max_ro.avitokinopoisk.data.database.models

data class RequestDBO(
    val startYear: Int?,
    val endYear: Int?,
    val isSeries: String? = null,
    val movieRatingStart: Double?,
    val movieRatingEnd: Double?,
    val startAgeRating: Int?,
    val endAgeRating: Int?,
    val genres: String? = null,
    val countries: String? = null,
    val networks: String? = null,
)

