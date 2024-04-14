package com.max_ro.avitokinopoisk.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO<E>(
    @SerialName("docs") val docs: List<E>,
    @SerialName("total") val total: Int,
    @SerialName("limit") val limit: Int,
    @SerialName("page") val page: Int,
    @SerialName("pages") val pages: Int

) {
}