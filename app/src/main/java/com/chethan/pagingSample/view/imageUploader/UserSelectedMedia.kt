package com.chethan.pagingSample.view.imageUploader

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chethan.pagingSample.MAX_VISUAL_CONTENT
import com.chethan.pagingSample.R
import com.chethan.pagingSample.model.IMediaItem
import com.chethan.pagingSample.model.MediaServerResponse
import com.chethan.pagingSample.model.NextItem
import com.chethan.pagingSample.model.UploadedItem
import com.chethan.pagingSample.repository.Status
import com.chethan.pagingSample.ui.theme.black33
import com.chethan.pagingSample.ui.theme.darkSkyBlue
import com.chethan.pagingSample.ui.theme.pastelGray
import com.chethan.pagingSample.ui.theme.semiTransparent
import com.chethan.pagingSample.utils.GlideApp
import com.chethan.pagingSample.utils.extension.replaceOne
import com.chethan.pagingSample.view.layout.VerticalGrid
import kotlinx.coroutines.Job
import timber.log.Timber

@Composable
fun DefaultPlaceHolder(onClickOfUpload: () -> Unit) {
    Column(
        modifier = Modifier
            .wrapContentSize(align = Alignment.TopStart)
            .wrapContentHeight()
            .background(pastelGray)
            .padding(20.dp)
    ) {

        Text(
            modifier = Modifier.wrapContentSize(align = Alignment.TopStart),
            text = stringResource(id = R.string.Post_Guest_Review_Step_1_User_Input_Photos_Video_Title),
            style = TextStyle(
                color = black33,
                fontSize = 14.sp
            )
        )

        val videoInstruction =
            stringResource(id = R.string.Post_Guest_Review_Step_1_User_Input_Photos_Video_Instructions).replaceOne(
                MAX_VISUAL_CONTENT.toString()
            )
        Text(
            modifier = Modifier
                .wrapContentSize(align = Alignment.TopStart)
                .padding(top = 8.dp),
            text = videoInstruction,
            style = TextStyle(
                color = black33,
                fontSize = 14.sp
            )
        )

        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(5.dp)
                )
                .indication(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
                .selectable(
                    enabled = true,
                    selected = true,
                    onClick = { }
                )
                .fillMaxWidth()
                .height(100.dp)
                .clickable(onClick = onClickOfUpload)
        ) {

            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_icon_upload),
                    contentDescription = ""
                )
                Text(
                    text = stringResource(id = R.string.Post_Guest_Review_Step_1_User_Input_Photos_Video_Upload),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    ),

                    )
            }
        }
    }
}

@Composable
fun SetUserSelectedMedia(
    listOfUploadedItem: List<IMediaItem>,
    clickOnNext: () -> Unit,
    deleteItem: (fileName: String) -> Unit
) {

    VerticalGrid(
        Modifier
            .wrapContentSize(align = Alignment.TopStart)
            .wrapContentHeight()
            .background(pastelGray)
            .padding(20.dp),
        columns = 3
    ) {

        val listWithNextItem = listOfUploadedItem.toMutableList()
        if (listWithNextItem.size < MAX_VISUAL_CONTENT)
            listWithNextItem.add(NextItem())

        Timber.i("Selected Image List : $listWithNextItem")
        listWithNextItem.forEach { mediaItem ->
            when (mediaItem) {
                is UploadedItem -> {
                    Timber.i("mediaItem : ${mediaItem.mediaInfo.fileName}")
                    SetSelectedMediaItemOnUI(mediaItem, deleteItem)
                }

                is NextItem -> {
                    SetNextItem(clickOnNext)
                }
            }
        }
    }
}

@Composable
fun SetNextItem(clickOnNext: () -> Unit?) {

    Box(
        modifier = Modifier
            .padding(5.dp)
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(5.dp))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(5.dp)
            )
            .indication(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .selectable(
                enabled = true,
                selected = true,
                onClick = { clickOnNext() }
            )
            .width(140.dp)
            .height(80.dp)
    ) {

        Column(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_icon_upload),
                contentDescription = ""
            )
            Text(
                text = stringResource(id = R.string.Post_Guest_Review_Step_1_User_Input_Photos_Video_Upload),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

data class ImageStatus(val isImageSet: Boolean, val image: ImageBitmap?)

@Composable
fun SetSelectedMediaItemOnUI(
    item: UploadedItem,
    deleteItem: (fileName: String) -> Unit,
    viewModel: WriteYourReviewViewModel = viewModel()
) {

    if (viewModel.reviewFormUserData.mediaUploadInformation.containsKey(item.mediaInfo.fileName)
            .not()
    )
        viewModel.reviewFormUserData.mediaUploadInformation[item.mediaInfo.fileName] = item

    val image = mutableStateOf(ImageStatus(false, null))
    val target = object : CustomTarget<Bitmap>() {
        override fun onLoadCleared(placeholder: Drawable?) {
        }

        override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
            image.value = ImageStatus(true, bitmap.asImageBitmap())
        }
    }

    GlideApp.with(LocalContext.current).asBitmap().override(400, 400)
        .load(item.mediaInfo.uri)
        .into(target)


    Box(
        modifier = Modifier
            .padding(5.dp)
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(5.dp))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(5.dp)
            )
            .indication(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .selectable(
                enabled = true,
                selected = true,
                onClick = { }
            )
            .width(140.dp)
            .height(80.dp)
    ) {

        image.value.image?.let {
            Image(
                bitmap = it,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_icon_delete_light),
            contentDescription = "",
            modifier = Modifier
                .wrapContentSize()
                .align(
                    Alignment.TopEnd
                )
                .padding(top = 4.dp, end = 4.dp)
                .clickable(
                    onClick = {
                        deleteItem(item.mediaInfo.fileName)
                    }
                )
        )

        /**
         *  As soon as bitmap is set to image, it will call below @UploadSelectedImageToRemote compose function to call network upload
         *  if the image is successfully upload and receive valid ID then that ID will be register in view model as single source of truth for later reference
         *  It is not required to check upload status before placing upload network call, ie because "remember{}" will call only once within @UploadSelectedImageToRemote,
         *  This would also mean that upload network call once and uses same live data instance to update progress bar irrespective of number of time @UploadSelectedImageToRemote composes,
         *  However it is required to check in one case to prevent uploading when it is restoring state back from privacy policy screen to write review, hence keeping below condition check
         */

        val uploadedItem =
            viewModel.reviewFormUserData.mediaUploadInformation[item.mediaInfo.fileName]
        if (uploadedItem?.mediaId.isNullOrEmpty())
            UploadSelectedImageToRemote(uri = item.mediaInfo.uri, job = item.job) {
                Timber.i("Uri : ${item.mediaInfo.fileName}    <---> Media Id : ${it.krakedUrl}")
                viewModel.reviewFormUserData.mediaUploadInformation[item.mediaInfo.fileName]?.apply {
                    mediaStatus = Status.SUCCESS
                    mediaId = it.krakedUrl
                }
            }
    }
}

@Composable
fun SetRetryUploading(onClickOfRetry: () -> Unit) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(semiTransparent)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_icon_reload),
            contentDescription = "",
            modifier = Modifier.clickable(onClick = { onClickOfRetry() })
        )
        Text(
            text = stringResource(id = R.string.Post_Guest_Review_Step_1_User_Input_Photos_Video_Retry),
            style = TextStyle(
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun UploadSelectedImageToRemote(
    job: Job,
    uri: Uri,
    imageUploaderViewModel: ImageUploaderViewModel = viewModel(),
    successfulResponse: (MediaServerResponse) -> Unit,
) {

    val context = LocalContext.current

    /**
     *  In compose, remember function call only once irrespective of number of time function calls from out side or inside.
     *  Hence not required to check the status of the upload to place call
     */
    val uploadImage =
        remember {
            mutableStateOf(imageUploaderViewModel.uploadImage(context, job, uri))
        }
    val imageUploadStatus = uploadImage.value.observeAsState()

    imageUploadStatus.value?.response?.let {

        if (it.isSuccessful) {
            it.body()?.let { response -> successfulResponse(response) }
        } else {
            SetRetryUploading {
                uploadImage.value = imageUploaderViewModel.uploadImage(context, Job(), uri)
            }
        }
    }

    imageUploadStatus.value?.uploadProgressValue?.let { uploadProgressValue ->
        ShowProgressBar(uploadProgressValue)
    }

}

@Composable
fun ShowProgressBar(value: Int = 0) {
    val isRequiredDismiss = value == 100
    val alpha = if (isRequiredDismiss) 0f else 1f
    val background = if (isRequiredDismiss) Color.Transparent else semiTransparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .alpha(alpha)
    ) {

        // progressbar
        val animatedProgress =
            animateFloatAsState(
                targetValue = (value / 100.0).toFloat()
            ).value

        LinearProgressIndicator(
            backgroundColor = Color.White,
            progress = animatedProgress,
            color = darkSkyBlue,
            modifier = Modifier
                .fillMaxWidth(0.7F)
                .align(Alignment.Center)
        )
    }
}
