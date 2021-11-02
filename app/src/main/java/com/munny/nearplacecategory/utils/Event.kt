package com.munny.nearplacecategory.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

class Event<T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}

fun<T> LiveData<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner, onHandle: (T) -> Unit) {
    observe(lifecycleOwner) {
        it.getContentIfNotHandled()?.let(onHandle)
    }
}