package com.task.digital

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.digital.data.*
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _items = MutableLiveData<Queue<ItemInfo>>()
    val items: LiveData<Queue<ItemInfo>> = _items

    init {
        val context = application.applicationContext
        Controller.printers.add(PrinterInfo(context, "Samsung", "MFP550", PrinterConnectivityStatus.ONLINE))
        Controller.printers.add(PrinterInfo(context, "HP", "LaserJet", PrinterConnectivityStatus.OFFLINE))
        Controller.printers.add(PrinterInfo(context, "Canon", "E510", PrinterConnectivityStatus.ONLINE))

        Controller.addFileToCurrentQueue("work", "pdf", 500)
        Controller.addFileToCurrentQueue("home", "txt", 100)
        Controller.addFileToCurrentQueue("crazy", "png", 1500)

        setItems(0)
    }

    fun setItems(printerPosition: Int) {
        Controller.current = printerPosition
        _items.value = Controller.getCurrentFileQueue()
    }
}