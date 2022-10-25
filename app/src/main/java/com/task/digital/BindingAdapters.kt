package com.task.digital

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.digital.data.ItemInfo
import com.task.digital.ui.ItemsListAdapter
import java.util.*

/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: Queue<ItemInfo>?) {
    val adapter = recyclerView.adapter as ItemsListAdapter
    Log.d("BindingAdapters", data?.toList().toString())
    adapter.submitList(data?.toList())
}