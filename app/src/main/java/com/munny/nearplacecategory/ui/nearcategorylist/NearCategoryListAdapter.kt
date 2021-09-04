package com.munny.nearplacecategory.ui.nearcategorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.munny.nearplacecategory._base.BaseListAdapter
import com.munny.nearplacecategory._base.BaseViewHolder
import com.munny.nearplacecategory.databinding.ItemNearCategoryListBinding
import com.munny.nearplacecategory.model.CategoryItem

class NearCategoryListAdapter(
    private val onCategoryClickListener: (CategoryItem) -> Unit
) : BaseListAdapter<CategoryItem>(DIFF_UTIL) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CategoryItem> {
        return NearCategoryListViewHolder(
            ItemNearCategoryListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).also { viewHolder ->
            viewHolder.itemView.setOnClickListener {
                val position = viewHolder.adapterPosition
                onCategoryClickListener.invoke(getItem(position))
            }
        }
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