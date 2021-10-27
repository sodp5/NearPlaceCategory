package com.munny.nearplacecategory.ui.storelist

import android.os.Bundle
import androidx.activity.viewModels
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory._base.BaseActivity
import com.munny.nearplacecategory.databinding.ActivityStoreListBinding
import com.munny.nearplacecategory.extensions.startActivity
import com.munny.nearplacecategory.model.CategoryItem
import com.munny.nearplacecategory.ui.storedetail.StoreDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StoreListActivity : BaseActivity<ActivityStoreListBinding>(R.layout.activity_store_list) {
    private val vm: StoreListViewModel by viewModels {
        val categoryItem = intent.extras?.getParcelable<CategoryItem>(EXTRA_CATEGORY_ITEM)
            ?: throw Exception("need CategoryItem params!!")

        StoreListViewModel.getFactory(factory, categoryItem.categoryName, categoryItem.placeList)
    }

    @Inject
    lateinit var factory: StoreListViewModel.AssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = vm

        binding.rvStoreList.adapter = StoreListAdapter { place ->
            startActivity<StoreDetailActivity>(Bundle().apply {
                putParcelable(StoreDetailActivity.EXTRA_PLACE, place)
            })
        }
    }

    companion object {
        const val EXTRA_CATEGORY_ITEM = "extra_category_item"
    }
}