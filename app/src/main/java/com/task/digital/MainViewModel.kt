package com.task.digital

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.digital.data.ItemInfo
import com.task.digital.data.JobStatus

class MainViewModel : ViewModel() {

    private val _items = MutableLiveData<List<ItemInfo>>()
    val items: LiveData<List<ItemInfo>> = _items

    init {
        _items.value = listOf(
            ItemInfo("work", "pdf", JobStatus.WAITING, 500),
            ItemInfo("home", "txt", JobStatus.WAITING, 1),
            ItemInfo("crazy", "png", JobStatus.WAITING, 5),
            )
    }
}