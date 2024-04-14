package com.max_ro.avitokinopoisk.features.movies_search.models

sealed interface DetailScreenViewEvent {
    data class SetIdForRequest(val id: String) : DetailScreenViewEvent

}

