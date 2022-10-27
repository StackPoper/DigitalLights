package com.task.digital

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.digital.data.*
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _items = MutableLiveData<Queue<ItemInfo>>()
    val items: LiveData<Queue<ItemInfo>> = _items

    val printers = ArrayList<PrinterInfo>()
    var currentPrinterChoice = 0

    init {
        setItems()
    }

    fun setItems() {
        if (printers.size > 0)
            _items.value = getCurrentPrinter().fileQueue
    }

    fun addPrinter(printer: PrinterInfo) {
        var alreadyAdded = false
        for (p in printers)
            if (p == printer)
                alreadyAdded = true
        if (!alreadyAdded)
            printers.add(printer)
    }

    fun addFileToCurrentPrinterQueue(name: String, type: String, sizeBytes: Long) {
        getCurrentPrinter().fileQueue.add(ItemInfo(name, type, status = JobStatus.WAITING, sizeBytes))
    }

    fun getCurrentPrinter() =
        printers[currentPrinterChoice]
}