package com.task.digital.data

import android.content.Context
import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

class PrinterInfo (
    private val context: Context?,
    private val name: String,
    private val model: String,
    private val connectivity: PrinterConnectivityStatus,
    val fileQueue: Queue<ItemInfo> = ConcurrentLinkedQueue()
)
{
    override fun toString(): String {
        return "$name $model ${context?.resources?.getString(connectivity.get())}"
    }
}