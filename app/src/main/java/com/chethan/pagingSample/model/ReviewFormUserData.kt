package com.chethan.pagingSample.model

import android.net.Uri

// Single source of truth to collect all the user interaction on UI
data class ReviewFormUserData(
    var nickName: String = "",
    var comment: String = "",
    var ageGroup: String = "",
    var ageGroupEnumName: String = "",
    var travelType: String = "",
    var travelTypeEnumName: String = "",
    var evaluationTotalScore: String = "",
    var mediaUploadInformation: HashMap<String, UploadedItem> = HashMap(),
    var evaluationScore: HashMap<String, String> = HashMap(),
    var postReviewTermsAccepted: Boolean? = false
) {

}
