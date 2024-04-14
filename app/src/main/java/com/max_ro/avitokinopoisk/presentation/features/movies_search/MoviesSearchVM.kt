package com.max_ro.avitokinopoisk.presentation.features.movies_search


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.max_ro.avitokinopoisk.data.dataStoreRecentReq.ResentReqDataStoreManager
import com.max_ro.avitokinopoisk.domain.models.MoviesRepository
import com.max_ro.avitokinopoisk.domain.models.MoviesEntity
import com.max_ro.avitokinopoisk.presentation.features.movies_search.models.SearchScreenViewEvent
import com.max_ro.avitokinopoisk.presentation.features.movies_search.models.SearchViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesSearchVM @Inject constructor(
    private val repository: MoviesRepository,
    private val resentReqDataStoreManager: ResentReqDataStoreManager
) : ViewModel() {

    private val _searchViewState = MutableStateFlow(SearchViewState())
    val searchViewState = _searchViewState.asStateFlow()

    private val _searchInputText = MutableStateFlow("")
    private val searchInputText: StateFlow<String> = _searchInputText.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchPagedData: Flow<PagingData<MoviesEntity>> = searchInputText
        .debounce(1000)
        .flatMapLatest { searchString ->
            repository.getSearchData(searchString)
        }


    fun handleEvent(event: SearchScreenViewEvent) {
        viewModelScope.launch {
            when (event) {
                is SearchScreenViewEvent.StartSearch -> {
                    _searchInputText.value = event.query
                }
                is SearchScreenViewEvent.SaveSuggestion -> resentReqDataStoreManager.saveToRecentRequest(event.suggestion)
                is SearchScreenViewEvent.GetSuggestion -> _searchViewState.update { state ->
                    state.copy(suggestions = resentReqDataStoreManager.getRecentRequest())
                }
            }
        }
    }
}




