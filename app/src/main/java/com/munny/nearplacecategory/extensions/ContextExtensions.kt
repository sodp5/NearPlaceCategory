package com.munny.nearplacecategory.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

inline fun <reified T : FragmentActivity> Context.startActivity(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)

    bundle?.let {
        intent.putExtras(it)
    }

    startActivity(intent)
}