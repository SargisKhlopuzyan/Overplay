package com.sargis.khlopuzyan.core.presenter.bindingAdapter

import android.util.TypedValue
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sargis.khlopuzyan.ui_common.R

@BindingAdapter("bindTextSize")
fun TextView.bindTextSize(fontSize: Float) {
    this.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
}

@BindingAdapter("bindSessionCount")
fun TextView.bindSessionCount(count: Int) {
    this.text = context.resources.getString(R.string.session_count, count)
}