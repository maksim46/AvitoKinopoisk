package com.max_ro.avitokinopoisk.data.network.pagingSources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.max_ro.avitokinopoisk.domain.common.toMoviesDbo
import com.max_ro.avitokinopoisk.data.database.MoviesDatabase
import com.max_ro.avitokinopoisk.data.database.models.MoviesDBO
import com.max_ro.avitokinopoisk.data.network.MoviesApi
import com.max_ro.avitokinopoisk.data.network.models.RequestDTO
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val db: MoviesDatabase,
    private val moviesApi: MoviesApi,
    private val filter: RequestDTO,
) : RemoteMediator<Int, MoviesDBO>() {

    private var pageIndex = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MoviesDBO>
    ): MediatorResult {

        pageIndex = getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
        return try {
            val response = moviesApi.getMovies(
                page = pageIndex,
                limit = state.config.pageSize,
                isSeries = filter.isSeries,
                year = filter.year,
                movieRating = filter.movieRating,
                ageRating = filter.ageRating,
                genres = filter.genres,
                countries = filter.countries,
                networks = filter.networks,
            )

            if (loadType == LoadType.REFRESH) {
            } else {
                val currentPage = checkNotNull(response.getOrThrow()).page
                val movieDtos = checkNotNull(response.getOrThrow().docs)
                val movieDbos = movieDtos.mapIndexed { index, movieDto ->
                    movieDto.toMoviesDbo(page = currentPage)
                }
                db.getMoviesDao().insert(movieDbos)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.getOrThrow().docs.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        return pageIndex
    }
}
