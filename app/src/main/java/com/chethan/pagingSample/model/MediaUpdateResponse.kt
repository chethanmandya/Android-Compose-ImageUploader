package com.chethan.pagingSample.model

import androidx.annotation.Keep
import retrofit2.Response
import java.io.Serializable

/**
 * Created by ts-chethan.boranna on 20,January,2020
 */

@Keep
data class MediaUpdateResponse(
    val response: Response<MediaServerResponse>?,
    val uploadProgressValue: Int
) :
    Serializable
