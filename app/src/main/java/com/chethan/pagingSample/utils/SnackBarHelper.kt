package com.chethan.pagingSample.utils

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.chethan.pagingSample.R
import com.google.android.material.snackbar.Snackbar

fun Fragment.presentSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_INDEFINITE
): Snackbar {
    this.requireView().let {
        return Snackbar.make(
            it,
            message,
            duration
        ).apply {

            setAction("Dismiss") {
                dismiss()
            }

            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                .maxLines = 10
            setActionTextColor(Color.RED)
            setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            ContextCompat.getColor(context, R.color.colorPrimary)
            show()
        }
    }
}

fun Fragment.presentSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_INDEFINITE,
    icon: Int
): Snackbar {
    this.requireView().let {
        return Snackbar.make(
            it,
            message,
            duration
        ).apply {

            val textView =
                view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.maxLines = 10
            textView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
            textView.compoundDrawablePadding =
                context.resources.getDimensionPixelOffset(R.dimen.dimen_8)
            textView.gravity = Gravity.TOP
            setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            ContextCompat.getColor(context, R.color.colorPrimary)
            show()
        }
    }
}

fun Activity.presentSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_INDEFINITE
): Snackbar {

    val parentLayout: View = findViewById(android.R.id.content)

    return Snackbar.make(
        parentLayout,
        message,
        duration
    ).apply {
        setAction("Dismiss") {
            dismiss()
        }

        parentLayout.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            ?.maxLines = 10
        setActionTextColor(Color.RED)
        setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        ContextCompat.getColor(context, R.color.colorPrimary)
        show()
    }
}
