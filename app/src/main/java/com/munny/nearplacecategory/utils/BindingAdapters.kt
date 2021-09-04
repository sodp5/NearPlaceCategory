package com.munny.nearplacecategory.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("distance")
fun distance(tv: TextView, meter: Int) {
    with(meter) {
        if (this >= 1000) {
            "${this / 1000.0}km"
        } else {
            "${this}m"
        }
    }.also {
        tv.text = it
    }
}