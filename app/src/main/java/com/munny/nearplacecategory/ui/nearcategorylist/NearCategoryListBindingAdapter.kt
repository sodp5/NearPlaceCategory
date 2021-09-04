package com.munny.nearplacecategory.ui.nearcategorylist

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.munny.nearplacecategory.model.CategoryItem

@BindingAdapter("setCategoryItems")
fun setCategoryItems(rv: RecyclerView, items: List<CategoryItem>?) {
    items?.let {
        (rv.adapter as NearCategoryListAdapter).submitList(it)
    }
}