package com.task.digital.data

data class ItemInfo (
    val name: String,
    val type: String,
    val status: JobStatus,
    val size: Int
) {
    override fun toString(): String {
        return "$name $type ${status.get()}"
    }
}