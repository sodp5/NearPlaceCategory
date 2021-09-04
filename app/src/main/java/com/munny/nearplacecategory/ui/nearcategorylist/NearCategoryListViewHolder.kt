package com.munny.nearplacecategory.ui.nearcategorylist

import com.munny.nearplacecategory._base.BaseViewHolder
import com.munny.nearplacecategory.databinding.ItemNearCategoryListBinding
import com.munny.nearplacecategory.model.CategoryItem

class NearCategoryListViewHolder(
    private val binding: ItemNearCategoryListBinding
) : BaseViewHolder<CategoryItem>(binding.root) {
    override fun bind(item: CategoryItem) {
        binding.category = item
    }
}