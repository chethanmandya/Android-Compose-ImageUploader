package com.chethan.pagingSample.view.venueSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.chethan.pagingSample.MAX_VISUAL_CONTENT
import com.chethan.pagingSample.R
import com.chethan.pagingSample.model.MediaInformation
import com.chethan.pagingSample.utils.extension.replaceOne
import com.chethan.pagingSample.utils.presentSnackBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageUploaderFragment : Fragment(), WriteReviewClickListener {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                venueSearchFragment()
            }
        }
    }

    @Composable
    @Preview
    fun venueSearchFragment() {
        UploadPhotos(this)
    }

    override fun onMediaError(errorState: MediaErrorState) {
        when (errorState) {
            is MediaErrorState.InvalidMediaSelection -> {
                userMessageForInvalidMediaSelection(errorState.list)
            }
            is MediaErrorState.SizeExceeded -> {
                userMessageMediaSizeExceeded(errorState.isSizeOverflow)
            }
        }
    }


    private fun userMessageMediaSizeExceeded(isSizeOverFlow: Boolean) {
        if (isSizeOverFlow) {
            val maximumVisualErrorUserMessage =
                getText(R.string.Post_Guest_Review_Step_1_User_Input_Photos_Video_Feedback).toString()
            presentSnackBar(
                maximumVisualErrorUserMessage
                    .replaceOne(
                        MAX_VISUAL_CONTENT.toString()
                    ),
                Snackbar.LENGTH_SHORT, R.drawable.ic_attention_white
            )
        }
    }

    private fun userMessageForInvalidMediaSelection(item: List<MediaInformation>) {

        val filesCantBeUploadedList = mutableListOf<String>()
        item.forEach {

            if (it.mediaType.contains("image/")) {
                filesCantBeUploadedList.add("${it.fileName}")
            } else if (it.mediaType.contains("video/")) {
                filesCantBeUploadedList.add("${it.fileName}")
            }
        }

        // Present message to the user for those files which can't be uploaded
        if (filesCantBeUploadedList.isNotEmpty()) {
            val listOfFiles =
                filesCantBeUploadedList.joinToString(
                    prefix = "",
                    postfix = "",
                    separator = ","
                )

            val videoSizeErrorUserMessage =
                getText(R.string.Post_Guest_Review_Step_1_User_Input_Photos_Video_Size_Error).toString()
            presentSnackBar(
                String.format(
                    videoSizeErrorUserMessage.replaceOne(
                        "$listOfFiles"
                    )
                ),
                Snackbar.LENGTH_SHORT, R.drawable.ic_attention_white
            )
        }
    }
}