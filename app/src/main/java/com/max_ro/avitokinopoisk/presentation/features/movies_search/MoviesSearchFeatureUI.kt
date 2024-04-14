package com.max_ro.avitokinopoisk.presentation.features.movies_search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.max_ro.avitokinopoisk.R
import com.max_ro.avitokinopoisk.data.dataStoreRecentReq.ResentRequest
import com.max_ro.avitokinopoisk.domain.models.MoviesEntity
import com.max_ro.avitokinopoisk.presentation.commonUi.ErrorStringMessage
import com.max_ro.avitokinopoisk.presentation.commonUi.LoadingNextPageItem
import com.max_ro.avitokinopoisk.presentation.commonUi.PageLoader
import com.max_ro.avitokinopoisk.presentation.commonUi.PosterImage
import com.max_ro.avitokinopoisk.presentation.features.movies_search.models.SearchScreenViewEvent
import com.max_ro.avitokinopoisk.presentation.features.movies_search.models.SearchViewState
import com.max_ro.avitokinopoisk.presentation.theme.MoviesTheme


@Composable
fun MovieSearchScreen(
    vm: MoviesSearchVM,
    onEvent: (SearchScreenViewEvent) -> Unit,
    onClickNavigateNext: () -> Unit,
    onClickNavigateBack: () -> Unit,
    onClickNavigateToDetail: (String) -> Unit,
) {
    val movies = vm.searchPagedData.collectAsLazyPagingItems()
    val searchState by vm.searchViewState.collectAsStateWithLifecycle()
    val currentSearchState = searchState

    DrawContent(
        movies = movies,
        viewState = currentSearchState,
        onEvent = { onEvent(it) },
        onClickBack = { onClickNavigateBack() },
        onClickNavigateToDetail = { onClickNavigateToDetail(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawContent(
    movies: LazyPagingItems<MoviesEntity>,
    viewState: SearchViewState,
    onEvent: (SearchScreenViewEvent) -> Unit,
    onClickBack: () -> Unit,
    onClickNavigateToDetail: (String) -> Unit,
) {

    val focusRequester = remember { FocusRequester() }
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val recentRequestsState = viewState.suggestions.collectAsState(initial = ResentRequest())

    LaunchedEffect(Unit) {
        onEvent(SearchScreenViewEvent.GetSuggestion)
        focusRequester.requestFocus()
    }
    Box(
        Modifier
            .semantics { isTraversalGroup = true }
            .zIndex(1f)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                Modifier
                    .semantics { isTraversalGroup = true }
                    .zIndex(1f)
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth()
            ) {
                AnimatedVisibility(
                    visible = !expanded,
                    enter = fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = 300)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 0, delayMillis = 0))
                ) {
                    ArrowBack(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 25.dp, end = 10.dp),
                        onClickBack = { onClickBack() },
                    )
                }
                DockedSearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .focusRequester(focusRequester)
                        .semantics { traversalIndex = -1f },
                    query = text,
                    onQueryChange = {
                        text = it
                        if (text.length > 1) {
                            onEvent(SearchScreenViewEvent.StartSearch(it))
                        }
                    },
                    onSearch = {
                        if (text.length > 1) {
                            onEvent(SearchScreenViewEvent.StartSearch(it))
                            onEvent(SearchScreenViewEvent.SaveSuggestion(it))
                            expanded = false
                        }
                    },
                    active = expanded,
                    onActiveChange = {
                        expanded = it
                    },
                    placeholder = { Text(stringResource(R.string.movie_or_TVseries)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search_icon)) },
                    trailingIcon = {
                        if (expanded) {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        if (text == "") {
                                            expanded = false
                                        } else {
                                            text = ""
                                        }
                                    },
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.search_icon)
                            )
                        }
                    },
                ) {
                    recentRequestsState.value.listOfRecentRequests.forEach {
                        Row(modifier = Modifier.padding(4.dp)) {
                            ListItem(
                                headlineContent = { Text(text = it) },
                                leadingContent = {
                                    Icon(
                                        modifier = Modifier.padding(end = 10.dp),
                                        imageVector = Icons.Default.History,
                                        contentDescription = stringResource(R.string.history_icon)
                                    )
                                },
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                modifier = Modifier
                                    .clickable {
                                        text = it
                                        expanded = false
                                        onEvent(SearchScreenViewEvent.StartSearch(it))
                                        onEvent(SearchScreenViewEvent.SaveSuggestion(it))
                                    }
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }

            LazyColumn {
                items(movies.itemCount) { index ->
                    movies[index]?.let {
                        MovieItem(
                            movie = it,
                            onClickNavigateToDetail = { onClickNavigateToDetail(it) }
                        )
                    }
                }
                movies.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = movies.loadState.refresh as LoadState.Error
                            item {
                                ErrorStringMessage()
                                /*ErrorMessage(
                                    modifier = Modifier.fillParentMaxSize(),
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })*/
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { LoadingNextPageItem(modifier = Modifier) }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = movies.loadState.append as LoadState.Error
                            item {
                                ErrorStringMessage()
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MovieItem(
    movie: MoviesEntity,
    onClickNavigateToDetail: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable { onClickNavigateToDetail(movie.id.toString()) },

        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            PosterImage(
                imageUrl = movie.poster.previewUrl,
                modifier = Modifier
                    .height(160.dp)
                    .width(100.dp)
                    .padding(top = 8.dp, bottom = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
            Spacer(modifier = Modifier.size(4.dp))
            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.Top) {
                Text(
                    text = movie.name,
                    style = MoviesTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = movie.shortDescription ?: stringResource(R.string.No_description),
                    style = MoviesTheme.typography.bodyMedium,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun ArrowBack(
    onClickBack: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier,
    ) {
        Icon(
            modifier = Modifier
                .clickable {
                    onClickBack()
                },
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = "History Icon"
        )
    }
}

