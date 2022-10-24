package com.task.digital.ui

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
            return areContentsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: ItemInfo, newItem: ItemInfo): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.type == newItem.type &&
                    oldItem.size == newItem.size
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        return ShowViewHolder(
            ItemInfoBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = getItem(position)
        holder.bind(show)
    }
}