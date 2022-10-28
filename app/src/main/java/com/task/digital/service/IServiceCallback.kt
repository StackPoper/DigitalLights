package com.task.digital.service

/**
 * A callback to update the UI after asynchronous work is done in [SpoolerService].
 */
interface IServiceCallback {
    fun updateUI()
}