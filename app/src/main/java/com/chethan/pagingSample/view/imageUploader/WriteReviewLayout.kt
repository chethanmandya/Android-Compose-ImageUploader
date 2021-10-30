package com.chethan.pagingSample.view.imageUploader

import android.Manifest
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chethan.pagingSample.MAX_VISUAL_CONTENT
import com.chethan.pagingSample.model.MediaInformation
import com.chethan.pagingSample.model.UploadedItem
import com.chethan.pagingSample.utils.checkSelfPermissionState
import timber.log.Timber

interface WriteReviewClickListener {
    fun onMediaError(errorState: MediaErrorState)
}

sealed class MediaErrorState {
    data class SizeExceeded(val isSizeOverflow: Boolean) : MediaErrorState()
    data class InvalidMediaSelection(val list: List<MediaInformation>) : MediaErrorState()
}


@Composable
fun UploadPhotos(
    listener: WriteReviewClickListener,
    viewModel: WriteYourReviewViewModel = viewModel(),
    imageUploaderViewModel: ImageUploaderViewModel = viewModel()
) {

    val mediaUploadInformation =
        remember { mutableStateOf(viewModel.reviewFormUserData.mediaUploadInformation) }
    val permission = checkSelfPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
    val hasPermission = permission?.hasPermission?.collectAsState(initial = false)

    val deleteItem: (fileName: String) -> Unit = { fileName ->
        Timber.i(" Delete ")

        mediaUploadInformation.value[fileName]?.run {
            if (mediaId.isEmpty().not())
                //imageUploaderViewModel.deleteUploadedImage(mediaId)
            else job.cancel()
        }

        val newList = mediaUploadInformation.value.toMutableMap().also {
            it.remove(fileName)
        }
        val newHashMap = HashMap(newList)
        mediaUploadInformation.value = newHashMap
        viewModel.reviewFormUserData.mediaUploadInformation = newHashMap
    }

    val mediaGallery = registerForMediaGallery(object : ISelectMediaFromGallery {
        override fun onSelectingInValidMedia(inValidList: List<MediaInformation>) {
            listener.onMediaError(MediaErrorState.InvalidMediaSelection(inValidList))
        }

        override fun onSelectingValidMedia(validMap: Map<String, UploadedItem>) {
            val newList = mediaUploadInformation.value.toMutableMap().also { existingMap ->
                validMap.forEach {
                    if (existingMap.values.size == MAX_VISUAL_CONTENT) {
                        listener.onMediaError(MediaErrorState.SizeExceeded(true))
                        return@also
                    }

                    if (existingMap.containsKey(it.key).not())
                        existingMap[it.key] = it.value
                }
            }

            val newHashMap = HashMap(newList)
            mediaUploadInformation.value = newHashMap
        }
    })

    if (mediaUploadInformation.value.isNullOrEmpty())
        DefaultPlaceHolder {
            hasPermission?.value?.let { permissionGranted ->
                if (!permissionGranted)
                    permission.launchPermissionRequest() // Launching run time permission
                else {
                    mediaGallery?.launchMediaGallery() // Launching media gallery
                }
            }
        }
    else {

        // we come to else block once user select images from gallery
        // It will recompose since we are listening to listOfValidMediaFiles through collectAsState
        val onClickOfNextUpload: () -> Unit = {
            mediaGallery?.launchMediaGallery()
        }

        SetUserSelectedMedia(
            mediaUploadInformation.value.map { it.value },
            onClickOfNextUpload,
            deleteItem
        )
    }
}



