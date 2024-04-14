package com.max_ro.avitokinopoisk.data.network.models

import com.max_ro.avitokinopoisk.data.network.Constant

data class RequestDTO(
    val page: Int = 1,
    val limit: Int? = 10,
    val selectFields: List<String> = Constant.SELECTED_FIELDS,
    val notNullFields: List<String> = Constant.NOT_NULL_FIELDS,
    val isSeries: String? = null,
    val year: String? = null,
    val movieRating: String? = null,
    val ageRating: String? = null,
    val genres: List<String>? = null,
    val countries: List<String>? = null,
    val networks: List<String>? = null,
)
