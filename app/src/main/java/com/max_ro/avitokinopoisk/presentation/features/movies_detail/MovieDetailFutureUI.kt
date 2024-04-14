package com.max_ro.avitokinopoisk.presentation.features.movies_detail


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.max_ro.avitokinopoisk.R
import com.max_ro.avitokinopoisk.domain.models.MoviesEntity
import com.max_ro.avitokinopoisk.domain.models.PostersEntity
import com.max_ro.avitokinopoisk.domain.models.ReviewEntity
import com.max_ro.avitokinopoisk.features.movies_search.models.DetailScreenViewEvent
import com.max_ro.avitokinopoisk.presentation.commonUi.AnimatedPlaceholder
import com.max_ro.avitokinopoisk.presentation.theme.MoviesTheme
import kotlinx.coroutines.launch

val headerHeight = 540.dp
val toolbarHeight = 56.dp
val collapseRange = headerHeight - toolbarHeight
val paddingMedium = 16.dp
val paddingSmall = 4.dp


@Composable
fun DetailScreen(
    vm: MoviesDetailVM,
    onEvent: (DetailScreenViewEvent) -> Unit,
    id: String?,
    onClickNavigateNext: () -> Unit,
    onClickNavigateBack: () -> Unit,
) {
    val posters = vm.postersFlow.collectAsLazyPagingItems()
    val reviews = vm.reviewsFlow.collectAsLazyPagingItems()

    val movie by vm.movieByIdFlow(id.toString()).collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(Unit) {
        if (id != null) {
            onEvent(DetailScreenViewEvent.SetIdForRequest(id))
        }
    }

    if (movie != null) {

        CollapsingToolbar(
            movie = movie!!,
            reviews = reviews,
            posters = posters,
            onClickBack = { onClickNavigateBack() }
        )
    } else {
        EmptyScreen()
    }
}

@Composable
private fun EmptyScreen() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.something_went_wrong))
    }

}

@Composable
private fun CollapsingToolbar(
    movie: MoviesEntity,
    reviews: LazyPagingItems<ReviewEntity>,
    posters: LazyPagingItems<PostersEntity>,
    onClickBack: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val collapseRangePx = with(LocalDensity.current) { collapseRange.toPx() }
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp


    val collapseRangeReached = remember {
        derivedStateOf {
            scrollState.value >= (collapseRangePx)
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = -available.y
                coroutineScope.launch {
                    if (scrollState.isScrollInProgress.not()) {
                        scrollState.scrollBy(delta)
                    }
                }
                return Offset.Zero
            }
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .zIndex(0f)

                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection)
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.height(screenHeightDp),

                    ) {
                    item(

                    ) {
                        Spacer(Modifier.height(headerHeight))
                    }
                    item(
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp),

                            text = movie.name,
                            style = TextStyle(
                                color = Color.Black, fontSize = 24.sp,
                                fontFamily = FontFamily.Serif,
                                fontWeight = W600
                            )
                        )
                    }
                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
                            thickness = 1.dp,
                            color = Color.Gray
                        )
                    }
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 20.dp),
                            text = movie.shortDescription ?: stringResource(R.string.no_description),
                            style = MoviesTheme.typography.titleMedium
                        )
                    }
                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
                            thickness = 1.dp,
                            color = Color.Gray
                        )
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(start = 20.dp),
                                text = "Рейтинг кинопоиска",
                                style = MoviesTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                modifier = Modifier
                                    .padding(end = 15.dp),
                                text = movie.movieRating ?: stringResource(R.string.No_rate),
                                style = MoviesTheme.typography.titleMedium
                            )
                        }
                    }

                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
                            thickness = 1.dp,
                            color = Color.Gray
                        )
                    }
                    item {
                        PostersSlidingCarousel(
                            posters = posters
                        )
                    }
                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
                            thickness = 1.dp,
                            color = Color.Gray
                        )
                    }
                    item {
                        ReviewSlidingCarousel(
                            reviews = reviews
                        )
                    }

                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 25.dp, top = 15.dp),
                            thickness = 1.dp,
                            color = Color.Gray
                        )
                    }

                }
            }
        }
    }
    Header(
        scrollState,
        collapseRangePx,
        Modifier.zIndex(1f),
        collapseRangeReached,
        movie
    )
    ToolbarActionsBack(
        modifier = Modifier
            .zIndex(4f)
            .statusBarsPadding(),
        onClickBack = { onClickBack() },
    )
}


@Composable
fun Header(
    scrollState: ScrollState,
    collapseRangePx: Float,
    modifier: Modifier,
    showToolbar: State<Boolean>,
    movie: MoviesEntity,
) {
    AnimatedVisibility(
        visible = !showToolbar.value,
        enter = fadeIn(animationSpec = tween(600)),
        exit = fadeOut(animationSpec = tween(600)),
        modifier = modifier
    ) {
        BoxWithConstraints(
            modifier = modifier
                .fillMaxWidth()
                .height(headerHeight)
                .graphicsLayer {
                    val collapseFraction = (scrollState.value / collapseRangePx).coerceIn(0f, 1f)
                    val yTranslation = lerp(
                        0.dp,
                        -(headerHeight - toolbarHeight),
                        collapseFraction
                    )
                    translationY = yTranslation.toPx()

                    val blur = lerp(0.dp, 3.dp, collapseFraction)
                    if (blur != 0.dp) {
                        renderEffect = BlurEffect(blur.toPx(), blur.toPx(), TileMode.Decal)
                    }
                }
        ) {
            val screenHeight = maxHeight
            val imageHeight = screenHeight * 2 / 3

            PosterDetailImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight),

                imageUrl = movie.poster.previewUrl
            )
        }
    }
}


@Composable
fun PosterDetailImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder
            (LocalContext.current)
            .data(data = imageUrl)
            .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                error(R.drawable.baseline_broken_image_24)
            }).build()
    )
    val state = painter.state

    if (state is AsyncImagePainter.State.Loading) {
        AnimatedPlaceholder(modifier)
    }

    GradientImage(
        painter = painter,
        contentDescription = stringResource(R.string.content_description_item_movie_poster),
        modifier = modifier,
    )
}

@Composable
fun ToolbarActionsBack(
    modifier: Modifier,
    onClickBack: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(toolbarHeight)
    ) {
        IconButton(
            onClick = { onClickBack() },
            modifier = modifier
                .padding(top = paddingMedium, start = paddingMedium)
                .clip(CircleShape)
                .size(32.dp)
                .background(
                    MoviesTheme.colorScheme.outline
                )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back Icon",
                tint = Color.White,
                modifier = Modifier.padding(paddingSmall)
            )
        }
    }
}


@Composable
fun GradientImage(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop,

            )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color.Transparent)
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomStart)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.White)
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(30.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.White, Color.Transparent)
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(30.dp)
                .align(Alignment.CenterEnd)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, Color.White)
                    )
                )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostersSlidingCarousel(
    posters: LazyPagingItems<PostersEntity>,
) {
    val pagerState = rememberPagerState(pageCount = { posters.itemCount })

    Column(modifier = Modifier) {
        Box(
            modifier = Modifier
                .height(250.dp)
        ) {
            Card(
                elevation = CardDefaults.cardElevation(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                AutoSlidingCarousel(
                    itemsCount = posters.itemCount,
                    pagerState = pagerState,
                    itemContent = { index ->
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(posters[index]?.previewUrl)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 5000,
    pagerState: PagerState,
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        HorizontalPager(state = pagerState) { page ->
            itemContent(page)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReviewSlidingCarousel(
    reviews: LazyPagingItems<ReviewEntity>,
) {
    val pagerState = rememberPagerState(pageCount = { reviews.itemCount })

    Column(modifier = Modifier) {
        Box(
            modifier = Modifier
                .heightIn(min = 250.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxSize()
            ) {
                AutoSlidingCarousel(
                    itemsCount = reviews.itemCount,
                    pagerState = pagerState,
                    itemContent = { index ->
                        Card(
                            modifier = Modifier,
                            elevation = CardDefaults.cardElevation(10.dp)
                        ) {
                            Column {
                                Text(
                                    modifier = Modifier.padding(5.dp),
                                    style = MoviesTheme.typography.bodyMedium,
                                    text = if (reviews[index] != null) {
                                        reviews[index]!!.review
                                    } else {
                                        stringResource(R.string.No_reviews)
                                    }
                                )
                                Text(
                                    modifier = Modifier.padding(5.dp),
                                    style = MoviesTheme.typography.labelMedium,
                                    text = if (reviews[index] != null) {
                                        reviews[index]!!.author
                                    } else {
                                        stringResource(R.string.Empty)
                                    }
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}




