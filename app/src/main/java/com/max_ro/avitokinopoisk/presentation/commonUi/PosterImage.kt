package com.max_ro.avitokinopoisk.presentation.commonUi

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.max_ro.avitokinopoisk.R

@Composable
fun PosterImage(
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
    Image(
        painter = painter,
        contentDescription = stringResource(R.string.content_description_item_movie_poster),
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}