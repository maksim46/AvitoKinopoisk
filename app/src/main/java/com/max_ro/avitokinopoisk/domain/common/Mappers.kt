package com.max_ro.avitokinopoisk.domain.common

import FullMoviesDTO
import com.max_ro.avitokinopoisk.domain.models.MoviesEntity
import com.max_ro.avitokinopoisk.domain.models.PosterEntity
import com.max_ro.avitokinopoisk.domain.models.PostersEntity
import com.max_ro.avitokinopoisk.data.database.models.RequestDBO
import com.max_ro.avitokinopoisk.domain.models.ReviewEntity
import com.max_ro.avitokinopoisk.data.database.models.MoviesDBO
import com.max_ro.avitokinopoisk.data.database.models.PosterDBO
import com.max_ro.avitokinopoisk.presentation.features.movies_main.models.MovieFilterStateViewState
import com.max_ro.avitokinopoisk.data.network.Constant
import com.max_ro.avitokinopoisk.data.network.models.MoviesDTO
import com.max_ro.avitokinopoisk.data.network.models.PostersDTO
import com.max_ro.avitokinopoisk.data.network.models.RequestDTO
import com.max_ro.avitokinopoisk.data.network.models.ReviewDTO

fun MoviesDBO.toMoviesEntity(): MoviesEntity {
    return MoviesEntity(
        id = this.id,
        name = this.name,
        year = this.year,
        poster = PosterEntity(url = this.poster.url, previewUrl = this.poster.previewUrl),
        shortDescription = this.shortDescription ?: "Нет описания",
        isSeries = this.isSeries,
        movieRating = if (this.movieRating != null) {
            this.movieRating.toString()
        } else {
            null
        },
        ageRating = if (this.ageRating != 1000) {
            this.ageRating.toString()
        } else {
            null
        },
        genres = this.genres,
        countries = this.countries,
        networks = this.networks,
    )
}

fun MoviesDTO.toMoviesDbo(page: Int): MoviesDBO {
    return MoviesDBO(
        id = this.id,
        name = this.name,
        year = this.year,
        poster = PosterDBO(url = this.posterDTO.url, previewUrl = this.posterDTO.previewUrl),
        shortDescription = this.shortDescription,
        isSeries = if (this.isSeries != null) {
            this.isSeries.toString()
        } else {
            null
        },
        movieRating = if (this.movieRating.kp != null) {
            this.movieRating.kp
        } else {
            null
        },

        ageRating = this.ageRating ?: 1000,
        genres = if (this.genres != null) {
            this.genres.mapNotNull { it.name }
        } else {
            null
        },
        countries = if (this.countries != null) {
            this.countries.mapNotNull { it.name }
        } else {
            null
        },
        networks =
        if (this.networks != null && this.networks.items != null) {
            this.networks.items.mapNotNull { it.name }
        } else {
            null
        },
        page = page,
        posters = null,
        reviewDTO = null
    )
}

fun FullMoviesDTO.toMoviesDBO(page: Int): MoviesDBO {
    return MoviesDBO(
        id = this.id,
        name = this.name,
        year = this.year,
        poster = PosterDBO(
            url =
            if (this.poster != null) {
                this.poster.url
            } else {
                null
            }, previewUrl = if (this.poster != null) {
                this.poster.previewUrl
            } else {
                null
            }
        ),
        shortDescription = this.shortDescription ?: "Нет описания",
        isSeries = if (this.isSeries != null) {
            this.isSeries.toString()
        } else {
            null
        },
        movieRating = if (this.rating != null) {
            this.rating.kp
        } else {
            null
        },
        ageRating = this.ageRating ?: 1000,
        genres = if (this.genres != null) {
            this.genres.mapNotNull { it.name }
        } else {
            null
        },
        countries = if (this.countries != null) {
            this.countries.mapNotNull { it.name }
        } else {
            null
        },
        networks = null,
        page = page,
        posters = null,
        reviewDTO = null

    )
}

fun MovieFilterStateViewState.toRequestDTO(): RequestDTO {
    return RequestDTO(
        page = 1,
        limit = 10,
        selectFields = Constant.SELECTED_FIELDS,
        notNullFields = Constant.NOT_NULL_FIELDS,
        isSeries = if (this.isSeries == "") {
            null
        } else {
            this.isSeries
        },
        year = "${this.yearFrom}-${this.yearTo}",
        movieRating =
        if (this.movieRating.first == 0 && this.movieRating.second == 10) {
            null
        } else {
            "${this.movieRating.first}-${this.movieRating.second}"
        },
        ageRating =
        if (this.ageRating.first == 0 && this.ageRating.second == 18) {
            null
        } else {
            "${this.ageRating.first}-${this.ageRating.second}"
        },
        genres = this.genres,
        countries = this.countries,
        networks = this.networks,
    )
}

fun ReviewDTO.toReviewEntity(): ReviewEntity {
    return ReviewEntity(
        movieId = this.movieId.toString(),
        review = this.review,
        author = this.author,
        type = this.type
    )
}

fun PostersDTO.toPosterEntity(): PostersEntity {
    return PostersEntity(
        movieId = this.movieId.toString(),
        previewUrl = this.previewUrl ?: "",
    )
}

fun MovieFilterStateViewState.toRequestDBO(): RequestDBO {
    return RequestDBO(
        startYear = this.yearFrom,
        endYear = this.yearTo,
        isSeries = if (this.isSeries == "") {
            null
        } else {
            this.isSeries
        },
        movieRatingStart = this.movieRating.first.toDouble(),
        movieRatingEnd = this.movieRating.second.toDouble(),
        startAgeRating = this.ageRating.first,
        endAgeRating = this.ageRating.second,
        genres = this.genres.joinToString(separator = ","),
        countries = this.countries.joinToString(separator = ","),
        networks = this.networks.joinToString(separator = ","),
    )
}
