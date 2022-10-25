package com.task.digital.data

object Controller {
    val printers = ArrayList<PrinterInfo>()
    var current = 0

    fun addFileToQueue(name: String, type: String, size: Int) =
        printers[current].fileQueue.add(ItemInfo(name, type, status = JobStatus.WAITING, size))

    fun getFileQueue() =
        printers[current].fileQueue
}