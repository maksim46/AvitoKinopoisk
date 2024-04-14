package com.max_ro.avitokinopoisk.presentation.features.movies_search.models

import com.max_ro.avitokinopoisk.data.dataStoreRecentReq.ResentRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


data class SearchViewState(
    val suggestions: Flow<ResentRequest> = flow { },
)




