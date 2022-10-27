package com.task.digital.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.task.digital.MainViewModel
import com.task.digital.data.ItemInfo
import com.task.digital.data.JobStatus
import com.task.digital.data.PrinterConnectivityStatus
import com.task.digital.data.PrinterInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpoolerService : Service() {
    private val binder = ServiceBinder()
    private lateinit var viewModel: MainViewModel

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    suspend fun initPrintersList(callback: IOnPrintersInitCallback) {
        viewModel.addPrinter(PrinterInfo(applicationContext, "Samsung", "MFP550", PrinterConnectivityStatus.ONLINE))
        viewModel.addPrinter(PrinterInfo(applicationContext, "HP", "LaserJet", PrinterConnectivityStatus.OFFLINE))
        viewModel.addPrinter(PrinterInfo(applicationContext, "Canon", "E510", PrinterConnectivityStatus.ONLINE))
        withContext(Dispatchers.Main) {
            callback.populateSpinner()
        }
    }

    suspend fun work(printer: PrinterInfo) {
        while (printer.isOnline() && printer.fileQueue.size > 0) {
            val item = printer.fileQueue.element()
            when (item.status) {
                JobStatus.DONE -> {
                    Thread.sleep(5000)
                    printer.fileQueue.poll()
                }
                JobStatus.IN_PROGRESS -> {
                    waitForPrintToFinish(item.sizeBytes)
                    item.status = JobStatus.DONE
                }
                JobStatus.WAITING ->
                    item.status = JobStatus.IN_PROGRESS
            }
            withContext(Dispatchers.Main) {
                viewModel.setItems()
            }
        }
    }

    inner class ServiceBinder : Binder() {
        fun getService(vm: MainViewModel): SpoolerService {
            viewModel = vm
            return this@SpoolerService
        }
    }

    private fun waitForPrintToFinish(size: Long) {
        val timeToPrint = PREDEFINED_TIME_TO_PRINT + size.div(100)
        Thread.sleep(timeToPrint)
    }

    companion object {
        private const val PREDEFINED_TIME_TO_PRINT = 4000 //ms
    }
}