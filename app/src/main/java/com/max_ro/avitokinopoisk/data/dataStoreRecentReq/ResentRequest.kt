package com.max_ro.avitokinopoisk.data.dataStoreRecentReq

import kotlinx.serialization.Serializable

@Serializable
data class ResentRequest (
    val listOfRecentRequests: List<String> = listOf(),
)