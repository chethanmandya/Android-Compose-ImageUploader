package com.chethan.pagingSample.view.venueSearch

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.chethan.pagingSample.model.ReviewFormUserData
import com.chethan.pagingSample.repository.ImageUploadRepository

class WriteYourReviewViewModel @ViewModelInject constructor(
    private val imageUploadRepository: ImageUploadRepository
) : ViewModel() {

    val reviewFormUserData = ReviewFormUserData()
}

