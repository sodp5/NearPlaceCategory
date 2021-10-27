package com.munny.nearplacecategory.ui.storedetail

import android.os.Bundle
import androidx.activity.viewModels
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory._base.BaseActivity
import com.munny.nearplacecategory.databinding.ActivityStoreDetailBinding
import com.munny.nearplacecategory.model.Place
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class StoreDetailActivity : BaseActivity<ActivityStoreDetailBinding>(
    R.layout.activity_store_detail
) {
    private val vm: StoreDetailViewModel by viewModels {
        val place = intent?.extras?.getParcelable<Place>(EXTRA_PLACE)
            ?: throw Exception("need Place params!!")

        StoreDetailViewModel.getFactory(assistedFactory, place)
    }

    @Inject
    lateinit var assistedFactory: StoreDetailViewModel.AssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = vm

        vm.getStaticMap(500, 500)
    }

    companion object {
        const val EXTRA_PLACE = "extra_place"
    }
}