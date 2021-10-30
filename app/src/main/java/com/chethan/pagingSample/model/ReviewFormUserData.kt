package com.chethan.pagingSample.model

// Single source of truth to collect all the user interaction on UI
data class ReviewFormUserData(
    var mediaUploadInformation: HashMap<String, UploadedItem> = HashMap(),
)