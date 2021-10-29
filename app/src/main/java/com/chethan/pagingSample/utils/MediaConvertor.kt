package com.chethan.pagingSample.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import com.chethan.pagingSample.UPLOAD_IMAGE_SIZE
import com.chethan.pagingSample.UPLOAD_VIDEO_SIZE
import com.chethan.pagingSample.model.MediaInformation
import timber.log.Timber

/**
 *  Filter out media images which can't be uploaded because of size restriction
 */
fun Uri.isMediaHasValidSize(context: Context): MediaInformation {

    var cursor: Cursor? = null
    var nameOfFile = ""
    var size: Long = 0
    var type = ""
    try {
        cursor = context.contentResolver.query(this, null, null, null, null)
        cursor?.let {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            it.moveToFirst()
            type = context.contentResolver.getType(this).orEmpty()
            size = it.getLong(sizeIndex)
            nameOfFile = it.getString(nameIndex).toString()
            Timber.i("Selected file type : $nameOfFile - $type, is the size exceeded : $size > ${UPLOAD_IMAGE_SIZE}")

            if (type.contains("image/")) {
                if (size > UPLOAD_IMAGE_SIZE) {
                    return MediaInformation(uri = this, false, nameOfFile, type, size)
                }
            } else if (type.contains("video/")) {

                if (size > UPLOAD_VIDEO_SIZE) {
                    return MediaInformation(uri = this, false, nameOfFile, type, size)
                }
            }
        }
    } finally {
        cursor?.close()
    }
    return MediaInformation(uri = this, true, nameOfFile, type, size)
}
