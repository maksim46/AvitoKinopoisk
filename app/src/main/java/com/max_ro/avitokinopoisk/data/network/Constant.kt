package com.max_ro.avitokinopoisk.data.network

class Constant {
    companion object {
        val SELECTED_FIELDS =
            listOf("id", "name", "year", "shortDescription", "poster", "isSeries", "rating", "ageRating", "genres", "networks", "countries")
        val NOT_NULL_FIELDS = listOf("id", "name", "year")
        val REVIEW_SELECTED_FIELDS = listOf("movieId", "review", "author", "type")
        val REVIEW_NOT_NULL_FIELDS = listOf("movieId", "review", "author", "type")
        val POSTER_SELECTED_FIELDS = listOf("movieId", "previewUrl")
        val POSTER_NOT_NULL_FIELDS = listOf("movieId", "previewUrl")
        val MAX_SUGGESTION_BUFFER_SIZE = 5
        val DEFAULT_PAGE_LIMIT = 10
    }
}
