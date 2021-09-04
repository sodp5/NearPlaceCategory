package com.munny.nearplacecategory.ui.nearcategorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.munny.nearplacecategory.databinding.ItemNearCategoryListBinding
import com.munny.nearplacecategory.model.CategoryItem

class NearCategoryListAdapter : ListAdapter<CategoryItem, NearCategoryListViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearCategoryListViewHolder {
        return NearCategoryListViewHolder(
            ItemNearCategoryListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NearCategoryListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<CategoryItem>() {
            override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
                return oldItem.categoryName == newItem.categoryName
            }
        }
    }
}