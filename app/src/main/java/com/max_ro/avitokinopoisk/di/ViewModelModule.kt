package com.max_ro.avitokinopoisk.di

import androidx.lifecycle.ViewModel
import com.max_ro.avitokinopoisk.presentation.features.movies_detail.MoviesDetailVM
import com.max_ro.avitokinopoisk.presentation.features.movies_main.MoviesMainVM
import com.max_ro.avitokinopoisk.presentation.features.movies_search.MoviesSearchVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(MoviesSearchVM::class)
    @Binds
    fun bindSearchViewModel(impl: MoviesSearchVM): ViewModel
    @IntoMap
    @ViewModelKey(MoviesMainVM::class)
    @Binds
    fun bindMovieViewModel(impl: MoviesMainVM): ViewModel
    @IntoMap
    @ViewModelKey(MoviesDetailVM::class)
    @Binds
    fun bindDetailViewModel(impl: MoviesDetailVM): ViewModel

}