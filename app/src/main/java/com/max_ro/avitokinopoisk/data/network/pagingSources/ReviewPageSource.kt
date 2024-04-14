package com.max_ro.avitokinopoisk.data.network.pagingSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.max_ro.avitokinopoisk.domain.common.toReviewEntity
import com.max_ro.avitokinopoisk.domain.models.ReviewEntity
import com.max_ro.avitokinopoisk.data.network.MoviesApi
import com.max_ro.avitokinopoisk.data.network.Constant.Companion.DEFAULT_PAGE_LIMIT
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class ReviewPageSource @AssistedInject constructor(
    private val moviesApi: MoviesApi,
    @Assisted("moviesId") private val movieId: String
) : PagingSource<Int, ReviewEntity>() {

    override fun getRefreshKey(state: PagingState<Int, ReviewEntity>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, ReviewEntity> {
        try {
            val pageNumber = params.key ?: 1
            val pageSize = params.loadSize.coerceAtMost(DEFAULT_PAGE_LIMIT)
            val response = moviesApi.getReview(page = pageNumber, limit = pageSize, movieId = movieId)

            if (response.isSuccessful) {
                val reviews = response.body()!!.docs.map { it.toReviewEntity() }
                val nextPageNumber = if (reviews.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                return LoadResult.Page(reviews, prevPageNumber, nextPageNumber)
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
        fun create(@Assisted("moviesId") query: String): ReviewPageSource
    }
}



