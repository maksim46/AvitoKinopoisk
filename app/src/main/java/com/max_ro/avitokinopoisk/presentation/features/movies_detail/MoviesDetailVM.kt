package com.max_ro.avitokinopoisk.presentation.features.movies_detail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.max_ro.avitokinopoisk.domain.models.MoviesRepository
import com.max_ro.avitokinopoisk.domain.models.MoviesEntity
import com.max_ro.avitokinopoisk.domain.models.PostersEntity
import com.max_ro.avitokinopoisk.domain.models.ReviewEntity
import com.max_ro.avitokinopoisk.features.movies_search.models.DetailScreenViewEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class MoviesDetailVM @Inject constructor(
    private val repository: MoviesRepository,
    private val reviewUseCaseProvider: Provider<ReviewUseCase>,
    private val postersUseCaseProvider: Provider<PostersUseCase>,
) : ViewModel() {


    private var newPagingSource: PagingSource<*, *>? = null

    private val _movieID = MutableStateFlow("")
    val movieId: StateFlow<String> = _movieID.asStateFlow()

    fun movieByIdFlow(movieId: String): Flow<MoviesEntity> {
        return repository.getMovieById(movieId)
    }

    private fun newPostersPager(movieId: String): Pager<Int, PostersEntity> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            val queryPostersUseCase = postersUseCaseProvider.get()
            queryPostersUseCase(movieId).also { newPagingSource = it }
        }
    }

    private fun newReviewPager(movieId: String): Pager<Int, ReviewEntity> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            val queryReviewUseCase = reviewUseCaseProvider.get()
            queryReviewUseCase(movieId).also { newPagingSource = it }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val reviewsFlow: StateFlow<PagingData<ReviewEntity>> = movieId
        .map(::newReviewPager)
        .flatMapLatest { pager -> pager.flow }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    @OptIn(ExperimentalCoroutinesApi::class)
    val postersFlow: StateFlow<PagingData<PostersEntity>> = movieId
        .map(::newPostersPager)
        .flatMapLatest { pager -> pager.flow }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    fun handleEvent(event: DetailScreenViewEvent) {
        viewModelScope.launch {
            when (event) {
                is DetailScreenViewEvent.SetIdForRequest -> {
                    _movieID.value = event.id
                }
                else -> {}
            }
        }
    }
}



