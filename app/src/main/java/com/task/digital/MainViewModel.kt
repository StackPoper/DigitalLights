package com.task.digital

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.digital.data.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _items = MutableLiveData<List<ItemInfo>>()
    val items: LiveData<List<ItemInfo>> = _items

    init {
        val context = application.applicationContext
        Controller.printers.add(PrinterInfo(context, "Samsung", "MFP550", PrinterConnectivityStatus.ONLINE))
        Controller.printers.add(PrinterInfo(context, "HP", "LaserJet", PrinterConnectivityStatus.OFFLINE))
        Controller.printers.add(PrinterInfo(context, "Canon", "E510", PrinterConnectivityStatus.ONLINE))

        Controller.addFileToQueue("work", "pdf", 500)
        Controller.addFileToQueue("home", "txt", 1)
        Controller.addFileToQueue("crazy", "png", 5)

        setItems(0)
    }

    fun setItems(printerPosition: Int) {
        Controller.current = printerPosition
        _items.value = Controller.getFileQueue().toList()
    }
}