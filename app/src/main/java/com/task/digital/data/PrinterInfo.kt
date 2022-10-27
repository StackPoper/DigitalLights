package com.task.digital.data

import android.content.Context
import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

data class PrinterInfo (
    private val context: Context?,
    private val name: String,
    private val model: String,
    private val connectivity: PrinterConnectivityStatus,
    val fileQueue: Queue<ItemInfo> = ConcurrentLinkedQueue(),
    var workStarted: Boolean = false
)
{
    override fun toString(): String {
        return "$name $model ${context?.resources?.getString(connectivity.get())}"
    }

    override fun equals(other: Any?) =
        other is PrinterInfo &&
                name == other.name &&
                model == other.model

    override fun hashCode(): Int {
        var result = context?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + model.hashCode()
        result = 31 * result + connectivity.hashCode()
        result = 31 * result + fileQueue.hashCode()
        result = 31 * result + workStarted.hashCode()
        return result
    }

    fun isOnline() =
        connectivity == PrinterConnectivityStatus.ONLINE
}