package com.task.digital.data

import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Holds data about the printer that will execute the job and the item that will be queued for printing.
 */
data class PrinterInfo (
    private val name: String,
    private val model: String,
    private val connectivity: PrinterConnectivityStatus,
    val fileQueue: Queue<ItemInfo> = ConcurrentLinkedQueue(),
    var workStarted: Boolean = false
)
{
    override fun toString(): String {
        return "$name $model ${connectivity.get()}"
    }

    override fun equals(other: Any?) =
        other is PrinterInfo &&
                name == other.name &&
                model == other.model

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + model.hashCode()
        result = 31 * result + connectivity.hashCode()
        result = 31 * result + fileQueue.hashCode()
        result = 31 * result + workStarted.hashCode()
        return result
    }

    fun isOnline() =
        connectivity == PrinterConnectivityStatus.ONLINE
}