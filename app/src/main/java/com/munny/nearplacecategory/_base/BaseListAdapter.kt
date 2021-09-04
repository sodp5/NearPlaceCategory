package com.munny.nearplacecategory._base

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<T>(
    diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<T>>(diffUtil) {
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }
}

@BindingAdapter("submitList")
fun submitList(rv: RecyclerView, items: List<Any>?) {
    items?.let {
        @Suppress("UNCHECKED_CAST")
        (rv.adapter as? BaseListAdapter<Any>)?.submitList(items)
    }
}