package com.max_ro.avitokinopoisk.domain.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

 class AppDispatchers(
    val default:CoroutineDispatcher = Dispatchers.Default,
    val io:CoroutineDispatcher = Dispatchers.IO,
    val main:MainCoroutineDispatcher = Dispatchers.Main,
    val unconfident:CoroutineDispatcher = Dispatchers.Unconfined,

)