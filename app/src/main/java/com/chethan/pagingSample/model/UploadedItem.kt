package com.chethan.pagingSample.model

import androidx.annotation.Keep
import com.chethan.pagingSample.repository.Status
import kotlinx.coroutines.Job


@Keep
data class UploadedItem(
    var mediaInfo: MediaInformation,
    var job: Job,
    var isNewlyAdded: Boolean
) : IMediaItem {
    override var mediaId = ""
    override var mediaStatus = Status.LOADING
}
