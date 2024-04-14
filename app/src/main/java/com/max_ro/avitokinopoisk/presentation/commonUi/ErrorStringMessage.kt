package com.max_ro.avitokinopoisk.presentation.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.max_ro.avitokinopoisk.R
import com.max_ro.avitokinopoisk.presentation.theme.MoviesTheme

@Composable
fun ErrorStringMessage() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MoviesTheme.colorScheme.error),
        contentAlignment = Alignment.Center
    )
    {
        Text(text = stringResource(R.string.Error_during_update), color = MoviesTheme.colorScheme.onError)
    }
}