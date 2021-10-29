package com.chethan.pagingSample.model

import android.net.Uri

data class MediaInformation(
    val uri : Uri,
    val isValidSize: Boolean,
    val fileName: String,
    val mediaType: String,
    val mediaSize: Long
)
