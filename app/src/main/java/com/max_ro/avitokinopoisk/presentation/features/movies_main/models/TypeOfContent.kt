package com.max_ro.avitokinopoisk.presentation.features.movies_main.models

sealed interface TypeOfContent {
    object IntContent : TypeOfContent
    object StringContent : TypeOfContent


}
