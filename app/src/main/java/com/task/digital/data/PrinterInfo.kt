package com.task.digital.data

import android.app.Activity

class PrinterInfo (
    private val context: Activity?,
    private val name: String,
    private val model: String,
    private val connectivity: PrinterConnectivityStatus
)
{
    override fun toString(): String {
        return "$name $model ${context?.resources?.getString(connectivity.get())}"
    }
}