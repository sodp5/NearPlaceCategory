package com.munny.nearplacecategory.ui.storelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.munny.nearplacecategory._base.BaseListAdapter
import com.munny.nearplacecategory._base.BaseViewHolder
import com.munny.nearplacecategory.databinding.ItemStoreListBinding
import com.munny.nearplacecategory.model.Place

class StoreListAdapter(
    private val onStoreClickListener: (place: Place) -> Unit
) : BaseListAdapter<Place>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Place> {
        return StoreListViewHolder(
            ItemStoreListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).also { viewHolder ->
            viewHolder.itemView.setOnClickListener {
                val position = viewHolder.adapterPosition
                onStoreClickListener.invoke(getItem(position))
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem == newItem
            }
        }

    }
}