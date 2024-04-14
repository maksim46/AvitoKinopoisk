package com.max_ro.avitokinopoisk.presentation.features.movies_main.models

sealed interface MainScreenViewEvent {
    data class IsFiltered (val data: MovieFilterStateViewState) : MainScreenViewEvent
    data class ClearFilters (val data: MovieFilterStateViewState) : MainScreenViewEvent
    data class SaveYearFrom(val yearFrom: Int) : MainScreenViewEvent
    data class SaveYearTo(val yearTo: Int) : MainScreenViewEvent
    data class IsInputError(val isInputError: Boolean) : MainScreenViewEvent
    data class SaveCountries(val countries: String) : MainScreenViewEvent
    data class DeleteCountries(val countries: String) : MainScreenViewEvent
    data class SaveGenres(val genre: String) : MainScreenViewEvent
    data class DeleteGenres(val genre: String) : MainScreenViewEvent
    data class SaveNetworks(val networks: String) : MainScreenViewEvent
    data class DeleteNetworks(val networks: String) : MainScreenViewEvent
    data class SaveAgeSlidePosition(val ageRatingSlidePosition: ClosedFloatingPointRange<Float>) : MainScreenViewEvent
    data class SaveIsSeries(val isSeries: String) : MainScreenViewEvent
    data class SaveMovieRatingSlidePosition(val movieRatingSlidePosition: ClosedFloatingPointRange<Float>) : MainScreenViewEvent


}

