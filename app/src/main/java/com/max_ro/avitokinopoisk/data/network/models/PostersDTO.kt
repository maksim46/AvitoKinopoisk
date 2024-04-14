package com.max_ro.avitokinopoisk.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostersDTO(
    @SerialName("previewUrl") val previewUrl: String?,
    @SerialName("movieId") val movieId: Int?,

    )
