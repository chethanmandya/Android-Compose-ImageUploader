package com.chethan.pagingSample.utils.extension

import android.content.Context
import android.content.ContextWrapper
import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.appcompat.app.AppCompatActivity
import com.chethan.pagingSample.GENERIC_REGULAR_EXPRESSION




fun Context.findActivity(): AppCompatActivity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) return context
        context = context.baseContext
    }
    return null
}

// This file will contains all the extension function
fun String.replace(vararg pairs: Pair<String, String>): String =
    pairs.fold(this) { acc, (old, new) -> acc.replace("{$old}", new, ignoreCase = true) }

fun String.replaceOnePair(pair: Pair<String, String>): String {

    return this.replace("{${pair.first}}", pair.second, ignoreCase = true)
}

// When we have single argument to replace
fun String.replaceOne(data: String): String =
    Regex(GENERIC_REGULAR_EXPRESSION).replaceFirst(this, data)

fun isNotNull(vararg data: Any?): Boolean {
    for (t in data) {
        if (t == null) return false
    }
    return true
}

fun String.replaceNewLine() = this.replace("\u005c\u006e", "\n")

// This method is used when if any parameter is not null
fun isAllNull(vararg data: Any?): Boolean {
    return data.filterNotNull().isEmpty()
}

inline fun SpannableStringBuilder.withSpan(
    span: Any,
    action: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder {
    val from = length
    action()
    setSpan(span, from, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}
