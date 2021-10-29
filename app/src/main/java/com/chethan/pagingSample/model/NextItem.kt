package com.chethan.pagingSample.model

import androidx.annotation.Keep
import com.chethan.pagingSample.repository.Status

@Keep
class NextItem : IMediaItem {
    override var mediaId = ""
    override var mediaStatus = Status.SUCCESS

}
