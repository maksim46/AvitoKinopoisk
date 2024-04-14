package com.max_ro.avitokinopoisk.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDTO(
    @SerialName("movieId") val movieId: Int,
    @SerialName("review") val review: String,
    @SerialName("author") val author: String,
    @SerialName("type") val type: String,
    @SerialName("page") val page: Int = 1,
)
