package com.max_ro.avitokinopoisk.data.dataStoreRecentReq

import androidx.datastore.core.Serializer
import com.max_ro.avitokinopoisk.domain.common.AppDispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ResentRequestSerializer : Serializer<ResentRequest> {
    override val defaultValue: ResentRequest
        get() = ResentRequest()

    override suspend fun readFrom(input: InputStream): ResentRequest {
        return try {
            Json.decodeFromString(
                deserializer = ResentRequest.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            ResentRequest()
        }
    }

    override suspend fun writeTo(t: ResentRequest, output: OutputStream) {
        withContext(AppDispatchers().io) {
            output.write(
                Json.encodeToString(
                    serializer = ResentRequest.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}