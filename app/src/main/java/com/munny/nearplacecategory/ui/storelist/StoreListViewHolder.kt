package com.munny.nearplacecategory.ui.storelist

import com.munny.nearplacecategory._base.BaseViewHolder
import com.munny.nearplacecategory.databinding.ItemStoreListBinding
import com.munny.nearplacecategory.model.Place

class StoreListViewHolder(
    private val binding: ItemStoreListBinding
) : BaseViewHolder<Place>(binding.root) {
    override fun bind(item: Place) {
        binding.place = item
    }
}