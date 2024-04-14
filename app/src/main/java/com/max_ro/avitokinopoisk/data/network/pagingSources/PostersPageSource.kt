package com.max_ro.avitokinopoisk.data.network.pagingSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.max_ro.avitokinopoisk.domain.common.toPosterEntity
import com.max_ro.avitokinopoisk.domain.models.PostersEntity
import com.max_ro.avitokinopoisk.data.network.MoviesApi
import com.max_ro.avitokinopoisk.data.network.Constant.Companion.DEFAULT_PAGE_LIMIT
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class PostersPageSource @AssistedInject constructor(
    private val moviesApi: MoviesApi,
    @Assisted("movieId") private val movieId: String
) : PagingSource<Int, PostersEntity>() {

    override fun getRefreshKey(state: PagingState<Int, PostersEntity>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, PostersEntity> {
        try {
            val pageNumber = params.key ?: 1
            val pageSize = params.loadSize.coerceAtMost(DEFAULT_PAGE_LIMIT)
            val response = moviesApi.getPosters(page = pageNumber, limit = pageSize, movieId = movieId)

            if (response.isSuccessful) {
                val posters = response.body()!!.docs.map { it.toPosterEntity() }
                val nextPageNumber = if (posters.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                return LoadResult.Page(posters, prevPageNumber, nextPageNumber)
            } else {
                return LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("movieId") query: String): PostersPageSource
    }
}


