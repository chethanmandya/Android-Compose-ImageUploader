package com.chethan.pagingSample.repository

import com.chethan.pagingSample.api.NetWorkApi
import com.chethan.pagingSample.api.ProgressRequestBody
import com.chethan.pagingSample.model.MediaServerResponse
import com.chethan.pagingSample.model.UploadCallbacks
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.File
import javax.inject.Singleton
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


@Singleton
class ImageUploadDataRepository constructor(private val services: NetWorkApi) :
    ImageUploadRepository {

    override suspend fun uploadImage(
        filePath: String,
        uploadCallbacks: UploadCallbacks
    ): Response<MediaServerResponse> {

        val file = File(filePath)
        val fileBody = ProgressRequestBody(file, "multipart/form-data", uploadCallbacks)
        val filePart =
            MultipartBody.Part.createFormData("upload", file.name, fileBody)

        val data: RequestBody = "{\"auth\":{\"api_key\": \"63281ac3a3f9ea0342598b634e996c0f\", \"api_secret\": \"258c0034149de213dcb0c239e1587864a55db3f9\"}, \"wait\":true}".toRequestBody()

        return services.uploadImage(filePart, data)
    }
}