package com.chethan.pagingSample.view.imageUploader

import android.content.Context
import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.chethan.pagingSample.model.MediaServerResponse
import com.chethan.pagingSample.model.MediaUpdateResponse
import com.chethan.pagingSample.model.UploadCallbacks
import com.chethan.pagingSample.repository.ImageUploadRepository
import com.chethan.pagingSample.utils.FileUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber

class ImageUploaderViewModel @ViewModelInject constructor(
    private val imageUploadRepository: ImageUploadRepository
) : ViewModel() {

    fun uploadImage(
        context: Context,
        job: Job,
        uri: Uri
    ): LiveData<MediaUpdateResponse> =

        liveData(viewModelScope.coroutineContext + Dispatchers.IO + handler + job) {


            val progressValueCallBacks = object : UploadCallbacks {
                override fun onProgressUpdate(percentage: Int) {

                    println(" percentage : $percentage")
                    viewModelScope.launch {
                        emit(
                            combineLatestData(
                                percentage, mediaServerResponse = null
                            )
                        )
                    }
                }
            }


            imageUploadRepository.uploadImage(
                FileUtils.getPath(context, uri),
                progressValueCallBacks
            ).apply {

                if (this.isSuccessful) {
                    emit(
                        combineLatestData(
                            100, mediaServerResponse = this
                        )
                    )
                }
            }


        }


    private fun combineLatestData(
        progressValue: Int,
        mediaServerResponse: Response<MediaServerResponse>?
    ): MediaUpdateResponse {

        return MediaUpdateResponse(mediaServerResponse, progressValue)
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.i("Caught  $exception")
    }
}