package com.munny.nearplacecategory.utils

import android.view.View
import androidx.databinding.BindingConversion

@BindingConversion
fun setVisibility(isVisible: Boolean): Int {
    return if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}