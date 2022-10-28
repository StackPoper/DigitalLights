package com.task.digital.data

/**
 * Status of the current [PrinterInfo] is connected to the network.
 */
enum class PrinterConnectivityStatus {
    ONLINE,
    OFFLINE;

    fun get() =
        when(this) {
            ONLINE -> "Online"
            OFFLINE -> "Offline"
        }
}