package com.chethan.pagingSample.model

import com.chethan.pagingSample.repository.Status


interface IMediaItem {

    var mediaId: String
    var mediaStatus: Status
}
