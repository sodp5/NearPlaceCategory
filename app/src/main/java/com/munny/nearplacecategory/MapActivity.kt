package com.munny.nearplacecategory

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.munny.nearplacecategory.api.PoiSearchApi
import com.munny.nearplacecategory.databinding.ActivityMapBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MapActivity : AppCompatActivity() {
    @Inject
    lateinit var api : PoiSearchApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        GlobalScope.launch {
            val result = api.getPoi("구갈동 하남돼지")

            Log.d("result", result.toString())
        }
    }
}