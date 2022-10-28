package com.task.digital

import android.widget.Spinner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.digital.data.ItemInfo
import com.task.digital.data.PrinterInfo
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class MainViewModel : ViewModel() {
    private val _items = MutableLiveData<Queue<ItemInfo>>()
    val items: LiveData<Queue<ItemInfo>> = _items

    val printers = ArrayList<PrinterInfo>()
    var currentPrinterChoice = 0

    init {
        setItems()
    }

    /**
     * Updates the [LiveData] that will be displayed.
     */
    fun setItems() {
        if (printers.size > 0)
            _items.value = getCurrentPrinter().fileQueue
    }


    /**
     * Adds a [PrinterInfo] to the list.
     */
    fun addPrinter(printer: PrinterInfo) {
        var alreadyAdded = false
        for (p in printers)
            if (p == printer)
                alreadyAdded = true
        if (!alreadyAdded)
            printers.add(printer)
    }


    /**
     * Adds an [ItemInfo] to the [PrinterInfo] [ConcurrentLinkedQueue].
     */
    fun addFileToCurrentPrinterQueue(file: ItemInfo) {
        getCurrentPrinter().fileQueue.add(file)
        setItems()
    }

    /**
     * Gets the current [PrinterInfo] previously chosen from the printers [Spinner].
     */
    fun getCurrentPrinter() =
        printers[currentPrinterChoice]
}