package com.chethan.pagingSample.repository

import com.chethan.pagingSample.model.MediaServerResponse
import com.chethan.pagingSample.model.UploadCallbacks
import retrofit2.Response

interface ImageUploadRepository {

    suspend fun uploadImage(
        filePath: String,
        uploadCallbacks: UploadCallbacks
    ): Response<MediaServerResponse>


}