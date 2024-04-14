package com.max_ro.avitokinopoisk.presentation.features.movies_search.models

sealed interface SearchScreenViewEvent {

    data class StartSearch(val query: String) : SearchScreenViewEvent
    data class SaveSuggestion(val suggestion: String) : SearchScreenViewEvent
    data object GetSuggestion : SearchScreenViewEvent

}

