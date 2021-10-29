package com.chethan.pagingSample.api

import com.chethan.pagingSample.model.MediaServerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Created by Chethan on 5/3/2019.
 * This interface contains the definition list of all the network endpoints used by the App.
 * Ref: Retrofit
 */
interface NetWorkApi {
    @Multipart
    @POST("v1/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("data") data: RequestBody,
    ): Response<MediaServerResponse>
}