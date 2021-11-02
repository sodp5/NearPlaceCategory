package com.munny.nearplacecategory.extensions

fun Boolean.ifTrue(function: () -> Unit): Boolean {
    if (this) {
        function.invoke()
    }

    return this
}

fun Boolean.ifFalse(function: () -> Unit): Boolean {
    if (!this) {
        function.invoke()
    }

    return this
}