package com.munny.nearplacecategory.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun<T, R> LiveData<T>.map(mapper: (T) -> R): LiveData<R> {
    return Transformations.map(this) {
        mapper.invoke(it)
    }
}