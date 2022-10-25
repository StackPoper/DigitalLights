package com.task.digital.data

object Controller {
    val printers = ArrayList<PrinterInfo>()
    var current = 0

    fun addFileToCurrentQueue(name: String, type: String, sizeBytes: Long) =
        printers[current].fileQueue.add(ItemInfo(name, type, status = JobStatus.WAITING, sizeBytes))

    fun getCurrentFileQueue() =
        printers[current].fileQueue

    fun getCurrentHeadStatus() =
        printers[current].fileQueue.element().status

    fun changeCurrentHeadStatus(newStatus: JobStatus) {
        printers[current].fileQueue.element().apply {
            status = newStatus
        }
    }

    fun pollCurrentHeadFromQueue() {
        printers[current].fileQueue.poll()
    }

    fun currentFileQueueHasElements() =
        printers[current].fileQueue.size > 0

    fun getCurrentHeadSize() =
        printers[current].fileQueue.element().sizeBytes
}