package com.max_ro.avitokinopoisk.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesDTO(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("year") val year: Int,
    @SerialName("poster") val posterDTO: PosterDTO,
    @SerialName("shortDescription") val shortDescription: String?,
    @SerialName("isSeries") val isSeries: Boolean?,
    @SerialName("rating") val movieRating: Rating,
    @SerialName("ageRating") val ageRating: Int?,
    @SerialName("genres") val genres: List<Genre>?,
    @SerialName("countries") val countries: List<Country>?,
    @SerialName("networks") val networks: NetworkContainer?,

    )

@Serializable
data class PosterDTO(
    @SerialName("url") val url: String? = null,
    @SerialName("previewUrl") val previewUrl: String? = null
)


@Serializable
data class Rating(
    @SerialName("kp") val kp: Double? = null,
    @SerialName("imdb") val imdb: Double? = null,
    @SerialName("filmCritics") val filmCritics: Double? = null,
    @SerialName("russianFilmCritics") val russianFilmCritics: Double? = null,
    @SerialName("await") val await: Double? = null
)

@Serializable
data class Genre(
    @SerialName("name") val name: String? = null
)

@Serializable
data class Country(
    @SerialName("name") val name: String? = null
)

@Serializable
data class NetworkContainer(
    val items: List<Network>?
)

@Serializable
data class Network(
    val name: String?
)