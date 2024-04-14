package com.max_ro.avitokinopoisk.presentation.features.movies_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.max_ro.avitokinopoisk.domain.models.MoviesEntity
import com.max_ro.avitokinopoisk.domain.models.MoviesRepository
import com.max_ro.avitokinopoisk.presentation.features.movies_main.models.ConstantFeatureMain
import com.max_ro.avitokinopoisk.presentation.features.movies_main.models.MainScreenViewEvent
import com.max_ro.avitokinopoisk.presentation.features.movies_main.models.MovieFilterStateViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class MoviesMainVM @Inject constructor(
    private val repository: MoviesRepository,
) : ViewModel() {

    private val _filterViewState = MutableStateFlow(
        MovieFilterStateViewState()
    )
    val filterViewState = _filterViewState.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    val moviePagedData: Flow<PagingData<MoviesEntity>> = filterViewState.flatMapLatest { query ->
        repository.getMediatorData(filterViewState.value)
    }


    fun handleEvent(event: MainScreenViewEvent) {
        viewModelScope.launch {
            when (event) {
                is MainScreenViewEvent.SaveCountries -> _filterViewState.update { state ->
                    state.copy(countries = mutableListOf<String>().apply {
                        state.countries.let { this.addAll(it) }
                        if (!this.contains(event.countries)) {
                            if (this.size > 4) {
                                removeAt(this.lastIndex)
                            }
                            this.add(0, event.countries)
                        }
                    })
                }

                is MainScreenViewEvent.DeleteCountries -> _filterViewState.update { state ->
                    state.copy(countries = mutableListOf<String>().apply {
                        state.countries.let { this.addAll(it) }
                        this.remove(event.countries)

                    })
                }

                is MainScreenViewEvent.SaveYearFrom -> _filterViewState.update { state ->
                    state.copy(yearFrom = event.yearFrom)
                }

                is MainScreenViewEvent.SaveYearTo -> _filterViewState.update { state ->
                    state.copy(yearTo = event.yearTo)
                }

                is MainScreenViewEvent.SaveIsSeries -> _filterViewState.update { state ->
                    state.copy(isSeries = event.isSeries)
                }

                is MainScreenViewEvent.SaveAgeSlidePosition -> _filterViewState.update { state ->
                    state.copy(
                        ageRatingSlidePosition = event.ageRatingSlidePosition,
                        ageRating = Pair(convertAgeRating(event.ageRatingSlidePosition).first, convertAgeRating(event.ageRatingSlidePosition).second)
                    )
                }

                is MainScreenViewEvent.SaveMovieRatingSlidePosition -> _filterViewState.update { state ->
                    state.copy(
                        movieRatingSlidePosition = event.movieRatingSlidePosition,
                        movieRating = Pair(event.movieRatingSlidePosition.start.toInt(), event.movieRatingSlidePosition.endInclusive.toInt())
                    )
                }

                is MainScreenViewEvent.SaveGenres -> _filterViewState.update { state ->
                    state.copy(genres = mutableListOf<String>().apply {
                        state.genres.let { this.addAll(it) }
                        if (!this.contains(event.genre)) {
                            if (this.size >= 3) {
                                removeAt(this.lastIndex)
                            }
                            this.add(0, event.genre)
                        }
                    })
                }

                is MainScreenViewEvent.DeleteGenres -> _filterViewState.update { state ->
                    state.copy(genres = mutableListOf<String>().apply {
                        state.genres.let { this.addAll(it) }
                        this.remove(event.genre)

                    })
                }

                is MainScreenViewEvent.SaveNetworks -> _filterViewState.update { state ->
                    state.copy(networks = mutableListOf<String>().apply {
                        state.networks.let { this.addAll(it) }
                        if (!this.contains(event.networks)) {
                            if (this.size >= 3) {
                                removeAt(this.lastIndex)
                            }
                            this.add(0, event.networks)
                        }
                    })
                }

                is MainScreenViewEvent.DeleteNetworks -> _filterViewState.update { state ->
                    state.copy(networks = mutableListOf<String>().apply {
                        state.networks.let { this.addAll(it) }
                        this.remove(event.networks)

                    })
                }

                is MainScreenViewEvent.IsInputError -> _filterViewState.update { state ->
                    state.copy(isYearInputError = event.isInputError)

                }

                is MainScreenViewEvent.IsFiltered -> _filterViewState.update { state ->
                    state.copy(isFiltered = checkIsFiltered(event.data))

                }

                is MainScreenViewEvent.ClearFilters -> _filterViewState.update { state ->
                    state.copy(
                        isFiltered = false,
                        yearFrom = ConstantFeatureMain.minYear,
                        yearTo = ConstantFeatureMain.maxYear,
                        countries = listOf(),
                        isYearInputError = false,
                        genres = listOf(),
                        networks = listOf(),
                        isSeries = "",
                        ageRatingSlidePosition = 0f..4f,
                        ageRating = Pair(
                            ConstantFeatureMain.ageRatingDiscreteValues[0],
                            ConstantFeatureMain.ageRatingDiscreteValues[ConstantFeatureMain.ageRatingDiscreteValues.lastIndex]
                        ),
                        movieRatingSlidePosition = 0f..10f,
                        movieRating = Pair(0, 10)
                    )
                }
            }
        }
    }
}


fun convertAgeRating(ageRatingSlidePosition: ClosedFloatingPointRange<Float>): Pair<Int, Int> {
    val start = ConstantFeatureMain.ageRatingDiscreteValues[ageRatingSlidePosition.start.toInt()]
    val end = ConstantFeatureMain.ageRatingDiscreteValues[ageRatingSlidePosition.endInclusive.toInt()]
    return Pair(start, end)

}

fun checkIsFiltered(data: MovieFilterStateViewState): Boolean {
    return data.yearFrom != ConstantFeatureMain.minYear
            || data.yearTo != ConstantFeatureMain.maxYear
            || data.countries.isNotEmpty()
            || data.genres.isNotEmpty()
            || data.networks.isNotEmpty()
            || data.isSeries != ""
            || data.ageRating != Pair(
        ConstantFeatureMain.ageRatingDiscreteValues[0],
        ConstantFeatureMain.ageRatingDiscreteValues[ConstantFeatureMain.ageRatingDiscreteValues.lastIndex]
    ) || data.movieRating != Pair(0, 10)
}



