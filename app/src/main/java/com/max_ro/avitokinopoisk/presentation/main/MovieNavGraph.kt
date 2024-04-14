package com.max_ro.avitokinopoisk.presentation.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.max_ro.avitokinopoisk.presentation.features.movies_detail.DetailScreen
import com.max_ro.avitokinopoisk.presentation.features.movies_detail.MoviesDetailVM

import com.max_ro.avitokinopoisk.presentation.features.movies_main.MoviesMainScreen
import com.max_ro.avitokinopoisk.presentation.features.movies_search.MovieSearchScreen
import com.max_ro.avitokinopoisk.presentation.features.movies_main.MoviesMainVM
import com.max_ro.avitokinopoisk.presentation.features.movies_search.MoviesSearchVM
import com.max_ro.avitokinopoisk.presentation.models.MoviesScreens

@Composable
fun MovieNavGraph(
    getViewModelFactory: () -> ViewModelProvider.Factory,
) {
   val vmMain: MoviesMainVM = viewModel(factory = getViewModelFactory())
    val  vmSearch: MoviesSearchVM = viewModel(factory = getViewModelFactory())
    val  vmDetail: MoviesDetailVM = viewModel(factory = getViewModelFactory())
    MoviesNavHost(
        mainViewModel = vmMain,
        searchViewModel = vmSearch,
        detailViewModel = vmDetail,
        startDestination = MoviesScreens.MovieMainScreen.route,

    )
}

@Composable
fun MoviesNavHost(
    mainViewModel: MoviesMainVM,
    searchViewModel: MoviesSearchVM,
    detailViewModel: MoviesDetailVM,
    startDestination: String,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = MoviesScreens.MovieMainScreen.route,
        ) {
            MoviesMainScreen(
                vm = mainViewModel,
                onEvent = mainViewModel::handleEvent,
                onClickNavigateNext = { navController.navigate(MoviesScreens.MovieSearchScreen.route) },
                onClickNavigateBack = {},
                onClickNavigateToDetail = { id -> navController.navigate(MoviesScreens.MovieDetailScreen.route + "/$id") }
            )
        }
        composable(
            route = MoviesScreens.MovieSearchScreen.route,
        ) { backStackEntry ->
            MovieSearchScreen(
                vm = searchViewModel,
                onEvent = searchViewModel::handleEvent,
                onClickNavigateNext = {},
                onClickNavigateBack = { navController.popBackStack() },
                onClickNavigateToDetail = { id -> navController.navigate(MoviesScreens.MovieDetailScreen.route + "/$id") }

            )
        }
        composable(
            route = MoviesScreens.MovieDetailScreen.route + "/{id}",
        ) { backStackEntry ->
            DetailScreen(
                vm = detailViewModel,
                onEvent = detailViewModel::handleEvent,
                id = backStackEntry.arguments?.getString("id"),
                onClickNavigateNext = {},
                onClickNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
