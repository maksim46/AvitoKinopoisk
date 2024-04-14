package com.max_ro.avitokinopoisk.domain.models

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.max_ro.avitokinopoisk.domain.common.LastFiveIntChecker
import com.max_ro.avitokinopoisk.domain.common.toMoviesEntity
import com.max_ro.avitokinopoisk.domain.common.toRequestDBO
import com.max_ro.avitokinopoisk.domain.common.toRequestDTO
import com.max_ro.avitokinopoisk.domain.models.MoviesEntity
import com.max_ro.avitokinopoisk.domain.models.PostersEntity
import com.max_ro.avitokinopoisk.domain.models.ReviewEntity
import com.max_ro.avitokinopoisk.data.network.pagingSources.PostersPageSource
import com.max_ro.avitokinopoisk.data.network.pagingSources.ReviewPageSource
import com.max_ro.avitokinopoisk.data.network.pagingSources.SearchRemoteMediator
import com.max_ro.avitokinopoisk.data.database.MoviesDatabase
import com.max_ro.avitokinopoisk.presentation.features.movies_main.models.MovieFilterStateViewState
import com.max_ro.avitokinopoisk.data.network.MoviesApi
import com.max_ro.avitokinopoisk.data.network.Constant
import com.max_ro.avitokinopoisk.data.network.pagingSources.MoviesRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val db: MoviesDatabase,
    private val api: MoviesApi,
    private val lastFiveIntChecker: LastFiveIntChecker,
    private val reviewPagingSourceFactory: ReviewPageSource.Factory,
    private val postersPagingSourceFactory: PostersPageSource.Factory,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getMediatorData(filter: MovieFilterStateViewState): Flow<PagingData<MoviesEntity>> {

        val pagingSourceFactory = {
            db.getMoviesDao().getAllMovie(
                startYear = filter.toRequestDBO().startYear,
                endYear = filter.toRequestDBO().endYear,
                movieRatingStart = filter.toRequestDBO().movieRatingStart,
                movieRatingEnd = filter.toRequestDBO().movieRatingEnd,
                startAgeRating = filter.toRequestDBO().startAgeRating,
                endAgeRating = filter.toRequestDBO().endAgeRating,
                isSeries = filter.toRequestDBO().isSeries,
                genres = filter.toRequestDBO().genres,
                countries = filter.toRequestDBO().countries,
                networks = filter.toRequestDBO().networks
            )
        }

        return Pager(
            config = PagingConfig(
                pageSize = Constant.DEFAULT_PAGE_LIMIT,
                enablePlaceholders = true,
            ),
            remoteMediator = MoviesRemoteMediator(
                db = db,
                moviesApi = api,
                filter = filter.toRequestDTO(),
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .map { pagingData ->
                pagingData.map {
                    it.toMoviesEntity()
                }
            }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getSearchData(query: String): Flow<PagingData<MoviesEntity>> {
        val pagingSourceFactory = {
            db.getMoviesDao().searchMoviesByName(
                searchQuery = query
            )
        }

        return Pager(
            config = PagingConfig(
                pageSize = Constant.DEFAULT_PAGE_LIMIT,
                enablePlaceholders = true,
            ),
            remoteMediator = SearchRemoteMediator(
                db = db,
                moviesApi = api,
                query = query,
                checkerInt = lastFiveIntChecker,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map {
                it.toMoviesEntity()
            }
        }

    }

    fun getMovieById(movieId: String): Flow<MoviesEntity> {
        return db.getMoviesDao().getOneMovie(movieId).map { it.toMoviesEntity() }
    }

    fun queryAllPosters(query: String): PagingSource<Int, PostersEntity> {
        return postersPagingSourceFactory.create(query)
    }

    fun queryAllReview(query: String): PagingSource<Int, ReviewEntity> {
        return reviewPagingSourceFactory.create(query)
    }
}

