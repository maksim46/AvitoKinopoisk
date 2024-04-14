package com.max_ro.avitokinopoisk.domain.models

data class ReviewEntity(
    val movieId: String,
    val review: String,
    val author: String,
    val type: String,
)