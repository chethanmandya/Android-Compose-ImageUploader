package com.chethan.pagingSample.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chethan.pagingSample.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Chethan on 5/3/2019.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
