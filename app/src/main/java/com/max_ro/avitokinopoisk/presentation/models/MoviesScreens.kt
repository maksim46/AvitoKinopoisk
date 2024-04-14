package com.max_ro.avitokinopoisk.presentation.models

sealed class MoviesScreens(val route:String) {
    data object MovieMainScreen : MoviesScreens("MovieMainScreen")
    data object MovieSearchScreen : MoviesScreens("MovieSearchScreen")
    data object MovieDetailScreen : MoviesScreens("MovieDetailScreen")
}