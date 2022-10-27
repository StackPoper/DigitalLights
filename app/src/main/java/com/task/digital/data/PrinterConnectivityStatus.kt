package com.task.digital.data

enum class PrinterConnectivityStatus {
    ONLINE,
    OFFLINE;

    fun get() =
        when(this) {
            ONLINE -> "Online"
            OFFLINE -> "Offline"
        }
}