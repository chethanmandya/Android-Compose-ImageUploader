package com.chethan.pagingSample.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MediaPicker(val context: Context) {

    fun getImagePickerSelectionPanel(): Intent {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val fname = "Image_Uploaded_example_$timeStamp"

        val sdImageMainDirectory = File(storageDir, fname)
        val outputFileUri = Uri.fromFile(sdImageMainDirectory)

        // Camera.
        val mediaIntents = ArrayList<Intent>()
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val packageManager = context.packageManager
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val packageName = res.activityInfo.packageName
            val intent = Intent(captureIntent)
            intent.component =
                ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(packageName)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            // cameraIntents.add(intent) // we are not adding camera, it is made for future requirement
        }

        // Gallery.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        // Filesystem.
        val fsIntent = Intent()
        // Supporting only for image as per change request
        fsIntent.type = "image/*" // "image/*,video/*"
        // Supporting only for image as per change request
        fsIntent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*")) // "image/*,video/*"
        fsIntent.action = Intent.ACTION_GET_CONTENT
        fsIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        mediaIntents.add(fsIntent)

        // Create the Chooser
        val chooserIntent = Intent.createChooser(galleryIntent, "Select Source")
        // Supporting only for image as per change request
        chooserIntent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*")) // "image/*,video/*"
        chooserIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            mediaIntents.toTypedArray<Parcelable>()
        )
        return chooserIntent
    }
}

