package com.task.digital.data

data class ItemInfo (
    val name: String,
    val type: String,
    var status: JobStatus,
    val sizeBytes: Long
) {
    override fun toString(): String {
        return "$name $type ${status.get()}"
    }
}