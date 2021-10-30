package com.chethan.pagingSample.view.imageUploader

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.chethan.pagingSample.MAX_VISUAL_CONTENT
import com.chethan.pagingSample.model.MediaInformation
import com.chethan.pagingSample.model.UploadedItem
import com.chethan.pagingSample.utils.MediaPicker
import com.chethan.pagingSample.utils.extension.findActivity
import com.chethan.pagingSample.utils.isMediaHasValidSize
import kotlinx.coroutines.Job

/**
 * Instantiate and manage it in composition like this
 */
@Composable
fun registerForMediaGallery(iSelectMediaFromGallery: ISelectMediaFromGallery): MediaGalleryState? {

    val context = LocalContext.current
    val activity = context.findActivity()
    val call = remember(activity) {
        activity?.let { RequestForMediaGallery(it, iSelectMediaFromGallery) }
    }
    // drive initialCheck and unregister from composition lifecycle
    DisposableEffect(call) {
        onDispose {
            call?.unregister()
        }
    }

    return call?.provideMediaGalleryState()
}

interface ISelectMediaFromGallery {
    fun onSelectingValidMedia(validMap: Map<String, UploadedItem>)
    fun onSelectingInValidMedia(inValidList: List<MediaInformation>)
}

class MediaGalleryState(
    val intent: Intent,
    private val mediaLauncher: ActivityResultLauncher<Intent>
) {
    fun launchMediaGallery() {
        mediaLauncher.launch(intent)
    }
}

private class RequestForMediaGallery(
    private val activity: AppCompatActivity,
    private val iSelectMediaFromGallery: ISelectMediaFromGallery
) {

    private var call: ActivityResultLauncher<Intent> = activity.activityResultRegistry.register(
        "RequestingForMediaGallery#",
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        onGalleryResult(result)
    }

    private var userIntent = MediaPicker(activity).getImagePickerSelectionPanel()

    fun unregister() {
        call.unregister()
    }

    fun provideMediaGalleryState(): MediaGalleryState {
        return MediaGalleryState(
            userIntent,
            call
        )
    }

    private fun onGalleryResult(result: ActivityResult) {

        val mediaList = mutableMapOf<String, UploadedItem>()
        val mediaListWithOverFlowSize = mutableListOf<MediaInformation>()

        // if there are more than one image / video picked
        // If only one image/video is selected,  it will not be in ClipData
        result.data?.clipData?.apply {
            for (i in 0 until itemCount) {
                val imageUri: Uri = getItemAt(i).uri
                if (mediaList.size < MAX_VISUAL_CONTENT)
                    onSelectingMedia(mediaList, imageUri, mediaListWithOverFlowSize)
            }
        }

        // if only single image / video picked
        if (result.data?.clipData == null && result.data?.data != null) {
            val uri = result.data
            uri?.data?.let { imageUri ->
                if (mediaList.size < MAX_VISUAL_CONTENT)
                    onSelectingMedia(mediaList, imageUri, mediaListWithOverFlowSize)
            }
        }

        iSelectMediaFromGallery.onSelectingValidMedia(mediaList)
        iSelectMediaFromGallery.onSelectingInValidMedia(mediaListWithOverFlowSize)
    }

    private fun onSelectingMedia(
        mediaList: MutableMap<String, UploadedItem>,
        imageUri: Uri,
        mediaListWithOverFlowSize: MutableList<MediaInformation>
    ) {

        val mediaInfo = imageUri.isMediaHasValidSize(activity)
        if (mediaInfo.isValidSize)
            mediaList[mediaInfo.fileName] = UploadedItem(mediaInfo, Job(), true)
        else
            mediaListWithOverFlowSize.add(mediaInfo)
    }
}
