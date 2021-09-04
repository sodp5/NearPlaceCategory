package com.munny.nearplacecategory.ui.nearcategorylist

import androidx.recyclerview.widget.RecyclerView
import com.munny.nearplacecategory.databinding.ItemNearCategoryListBinding
import com.munny.nearplacecategory.model.CategoryItem

class NearCategoryListViewHolder(
    private val binding: ItemNearCategoryListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(categoryItem: CategoryItem) {
        binding.category = categoryItem
    }
}