package com.max_ro.avitokinopoisk.presentation.features.movies_detail

import androidx.paging.PagingSource
import com.max_ro.avitokinopoisk.domain.models.MoviesRepository
import com.max_ro.avitokinopoisk.domain.models.ReviewEntity
import javax.inject.Inject

class ReviewUseCase @Inject constructor(private val repository: MoviesRepository) {
    operator fun invoke(movieId: String): PagingSource<Int, ReviewEntity> {
        return repository.queryAllReview(movieId)
    }
}