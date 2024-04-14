package com.max_ro.avitokinopoisk.data.network.pagingSources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.max_ro.avitokinopoisk.domain.common.LastFiveIntChecker
import com.max_ro.avitokinopoisk.domain.common.toMoviesDBO
import com.max_ro.avitokinopoisk.data.database.MoviesDatabase
import com.max_ro.avitokinopoisk.data.database.models.MoviesDBO
import com.max_ro.avitokinopoisk.data.network.MoviesApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val db: MoviesDatabase,
    private val moviesApi: MoviesApi,
    private val query: String,
    private val checkerInt: LastFiveIntChecker,
) : RemoteMediator<Int, MoviesDBO>() {

    private var pageIndex = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MoviesDBO>
    ): MediatorResult {

        pageIndex = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    1
                } else {
                    pageIndex + 1
                }
            }
        }

        return try {
            val response = moviesApi.searchMovies(
                page = pageIndex,
                limit = state.config.pageSize,
                query = query
            )

            val currentPage = checkNotNull(response.getOrThrow()).page
            val movieDtos = checkNotNull(response.getOrThrow().docs)
            val movieDbos = movieDtos.mapIndexed { index, movieDto ->
                movieDto.toMoviesDBO(page = currentPage)
            }
            db.getMoviesDao().insert(movieDbos)

            val itemsCountFromDb = db.getMoviesDao().countMoviesByName(query)

            if (checkerInt.addItem(itemsCountFromDb)) {
                return MediatorResult.Success(endOfPaginationReached = true)
            } else {
                MediatorResult.Success(
                    endOfPaginationReached = response.getOrThrow().docs.isEmpty()
                )
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}