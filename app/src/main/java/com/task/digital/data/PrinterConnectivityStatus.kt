package com.task.digital.data

import com.task.digital.R

enum class PrinterConnectivityStatus {
    ONLINE,
    OFFLINE;

    fun get() =
        when(this) {
            ONLINE -> R.string.printer_status_online
            OFFLINE -> R.string.printer_status_offline
        }
}