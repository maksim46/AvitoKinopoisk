package com.max_ro.avitokinopoisk.data.dataStoreRecentReq

import android.content.Context
import androidx.datastore.dataStore
import com.max_ro.avitokinopoisk.data.network.Constant.Companion.MAX_SUGGESTION_BUFFER_SIZE
import javax.inject.Inject

private val Context.recentRequestsDataStore by dataStore("resent_requests", ResentRequestSerializer)

class ResentReqDataStoreManager @Inject constructor(val context: Context) {
    private val bufferSize = MAX_SUGGESTION_BUFFER_SIZE
    suspend fun saveToRecentRequest(request: String) {
        context.recentRequestsDataStore.updateData { data ->
            val list = data.listOfRecentRequests.toMutableList()
            if (!list.contains(request)) {
                list.add(0, request)
                if (list.size > bufferSize) list.removeAt(bufferSize)
                data.copy(listOfRecentRequests = list)
            } else{
                list.removeAt(list.indexOf(request))
                list.add(0,request)
                data.copy(listOfRecentRequests = list)
            }
        }
    }

    fun getRecentRequest() = context.recentRequestsDataStore.data
}
