package com.task.digital

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.digital.data.ItemInfo
import com.task.digital.ui.ItemsListAdapter

/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ItemInfo>?) {
    val adapter = recyclerView.adapter as ItemsListAdapter
    adapter.submitList(data)
}