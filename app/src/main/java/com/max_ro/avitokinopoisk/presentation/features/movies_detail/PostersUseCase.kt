package com.max_ro.avitokinopoisk.presentation.features.movies_detail

import androidx.paging.PagingSource
import com.max_ro.avitokinopoisk.domain.models.MoviesRepository
import com.max_ro.avitokinopoisk.domain.models.PostersEntity
import javax.inject.Inject

class PostersUseCase @Inject constructor(private val repository: MoviesRepository) {
    operator fun invoke(movieId: String): PagingSource<Int, PostersEntity> {
        return repository.queryAllPosters(movieId)
    }
}