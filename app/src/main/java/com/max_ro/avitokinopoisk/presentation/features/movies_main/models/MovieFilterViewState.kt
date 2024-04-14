package com.max_ro.avitokinopoisk.presentation.features.movies_main.models


data class MovieFilterStateViewState(
    val isFiltered: Boolean = false,
    val yearFrom: Int = ConstantFeatureMain.minYear,
    val yearTo: Int = ConstantFeatureMain.maxYear,
    val year: String? = null,
    val isYearInputError: Boolean = false,
    val countries: List<String> = listOf(),
    val genres: List<String> = listOf(),
    val networks: List<String> = listOf(),
    val isSeries: String = "",
    val ageRatingSlidePosition: ClosedFloatingPointRange<Float> = 0f..4f,
    val ageRating: Pair<Int, Int> = Pair(
        ConstantFeatureMain.ageRatingDiscreteValues[0],
        ConstantFeatureMain.ageRatingDiscreteValues[ConstantFeatureMain.ageRatingDiscreteValues.lastIndex]
    ),
    val movieRatingSlidePosition: ClosedFloatingPointRange<Float> = 0f..10f,
    val movieRating: Pair<Int, Int> = Pair(0, 10),
)


