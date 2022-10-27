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

    override fun equals(other: Any?) =
        other is ItemInfo &&
                name == other.name &&
                type == other.type &&
                sizeBytes == other.sizeBytes

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + sizeBytes.hashCode()
        return result
    }
}