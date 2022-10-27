package com.task.digital.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.digital.data.ItemInfo
import com.task.digital.databinding.ItemInfoBinding

class ItemsListAdapter:
    ListAdapter<ItemInfo, ItemsListAdapter.ShowViewHolder>(DiffCallback) {

    /**
     * The ShowViewHolder constructor takes the binding variable from the associated
     * ShowInfoItem, which gives it access to the [ItemInfo] information.
     */
    class ShowViewHolder(private var binding: ItemInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemInfo: ItemInfo) {
            binding.item = itemInfo
            // Forces the data binding to execute immediately, which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [ItemInfo] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ItemInfo>() {
        override fun areItemsTheSame(oldItem: ItemInfo, newItem: ItemInfo): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: ItemInfo, newItem: ItemInfo): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.type == newItem.type &&
                    oldItem.sizeBytes == newItem.sizeBytes &&
                    oldItem.status == newItem.status
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        return ShowViewHolder(
            ItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("BindingAdapters", "onBindViewHolder:$item")
        holder.bind(item)
    }
}