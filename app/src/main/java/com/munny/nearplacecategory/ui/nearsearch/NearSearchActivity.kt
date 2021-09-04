package com.munny.nearplacecategory.ui.nearsearch

import android.os.Bundle
import com.munny.nearplacecategory.MapActivity
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory._base.BaseActivity
import com.munny.nearplacecategory.databinding.ActivityNearSearchBinding
import com.munny.nearplacecategory.extensions.startActivity

class NearSearchActivity : BaseActivity<ActivityNearSearchBinding>(
    R.layout.activity_near_search
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnSearchNearPlace.setOnClickListener {
            startActivity<MapActivity>()
            finish()
        }
    }
}