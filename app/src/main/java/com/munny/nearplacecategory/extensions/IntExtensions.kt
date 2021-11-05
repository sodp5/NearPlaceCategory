package com.munny.nearplacecategory.extensions

fun Int.toDistance(): String {
    return if (this >= 1000) {
        "${this / 1000.0}km"
    } else {
        "${this}m"
    }
}