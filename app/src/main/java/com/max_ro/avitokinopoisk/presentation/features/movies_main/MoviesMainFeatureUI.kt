package com.max_ro.avitokinopoisk.presentation.features.movies_main


import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.max_ro.avitokinopoisk.R
import com.max_ro.avitokinopoisk.domain.models.MoviesEntity
import com.max_ro.avitokinopoisk.presentation.commonUi.ErrorMessage
import com.max_ro.avitokinopoisk.presentation.commonUi.ErrorStringMessage
import com.max_ro.avitokinopoisk.presentation.commonUi.LoadingNextPageItem
import com.max_ro.avitokinopoisk.presentation.commonUi.PageLoader
import com.max_ro.avitokinopoisk.presentation.commonUi.PosterImage
import com.max_ro.avitokinopoisk.presentation.features.movies_main.models.MainScreenViewEvent
import com.max_ro.avitokinopoisk.presentation.features.movies_main.models.MovieFilterStateViewState
import com.max_ro.avitokinopoisk.presentation.theme.MoviesTheme
import kotlinx.coroutines.launch

@Composable
fun MoviesMainScreen(
    vm: MoviesMainVM,
    onEvent: (MainScreenViewEvent) -> Unit,
    onClickNavigateNext: () -> Unit,
    onClickNavigateBack: () -> Unit,
    onClickNavigateToDetail: (String) -> Unit,
) {

    val filterViewState by vm.filterViewState.collectAsStateWithLifecycle()
    val currentFilterViewState = filterViewState

    val movies = vm.moviePagedData.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {

        DrawContent(
            movies = movies,
            onClicks = { onClickNavigateNext() },
            currentFilterViewState = currentFilterViewState,
            onEvent = { onEvent(it) },
            onClickNavigateToDetail = { onClickNavigateToDetail(it) },
        )
    }
}

@Composable
fun DrawContent(
    movies: LazyPagingItems<MoviesEntity>,
    onClicks: (text: String) -> Unit,
    currentFilterViewState: MovieFilterStateViewState,
    onEvent: (MainScreenViewEvent) -> Unit,
    onClickNavigateToDetail: (String) -> Unit,
) {
    Column {
        Row {
            SearchField(
                { onClicks(it) },
                Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
                    .fillMaxWidth()
            )
            FilterModalBottomSheet(
                currentFilterViewState = currentFilterViewState,
                onEvent = { onEvent(it) },
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(movies.itemCount) { index ->
                movies[index]?.let {
                    MovieMainItem(
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
          /*                  ErrorMessage(
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
             /*               ErrorMessage(
                                modifier = Modifier,
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })*/
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchField(
    onClick: (text: String) -> Unit,
    modifier: Modifier,
) {
    SearchBar(
        modifier = modifier,
        query = "",
        onQueryChange = {},
        onSearch = { },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        placeholder = { Text(text = stringResource(R.string.movies_and_tv)) },
        active = false,
        onActiveChange = {
            onClick("as")
        },
    )
    {
    }
}


@Composable
fun MovieMainItem(
    movie: MoviesEntity,
    onClickNavigateToDetail: (String) -> Unit,

    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickNavigateToDetail(movie.id.toString()) }
            .padding(6.dp),

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
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = movie.shortDescription ?: "Нет описания",
                    style = MoviesTheme.typography.bodyMedium,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModalBottomSheet(
    currentFilterViewState: MovieFilterStateViewState,
    onEvent: (MainScreenViewEvent) -> Unit,
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    Box(
        modifier = Modifier
            .padding(top = 25.dp, start = 10.dp, end = 10.dp),
    ) {
        Icon(
            modifier = Modifier
                .clickable {
                    openBottomSheet = !openBottomSheet
                },
            imageVector =
            if (currentFilterViewState.isFiltered) {
                Icons.Default.FilterAlt
            } else {
                Icons.Default.Tune
            },
            contentDescription = "Filter Icon"
        )

        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = !openBottomSheet },
                sheetState = bottomSheetState,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .weight(0.89f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                        {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        scope
                                            .launch { bottomSheetState.hide() }
                                            .invokeOnCompletion {
                                                if (!bottomSheetState.isVisible) {
                                                    openBottomSheet = false
                                                }
                                            }
                                    }
                                    .padding(start = 26.dp, top = 14.dp, bottom = 4.dp),
                                imageVector =
                                Icons.Default.ArrowBackIosNew,
                                contentDescription = "Filter Icon"
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                modifier = Modifier
                                    .padding(end = 26.dp, bottom = 4.dp),
                                onClick = { onEvent(MainScreenViewEvent.ClearFilters(currentFilterViewState)) }
                            ) {
                                Text(stringResource(R.string.Drop))
                            }
                        }
                        FilterScreen(viewState = currentFilterViewState,
                            onEvent = { onEvent(it) }
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 40.dp),
                        thickness = 1.dp,
                        color = Color.Transparent
                    )
                }
            }
        }
    }
}
