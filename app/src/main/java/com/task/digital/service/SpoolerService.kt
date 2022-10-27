package com.task.digital.service

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import com.task.digital.FileLoader
import com.task.digital.MainViewModel
import com.task.digital.data.JobStatus
import com.task.digital.data.PrinterConnectivityStatus
import com.task.digital.data.PrinterInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SpoolerService : Service() {
    private val binder = ServiceBinder()
    private lateinit var viewModel: MainViewModel

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    suspend fun initPrintersList(callback: IOnPrintersInitCallback) {
        viewModel.addPrinter(PrinterInfo("Samsung", "MFP550", PrinterConnectivityStatus.ONLINE))
        viewModel.addPrinter(PrinterInfo("HP", "LaserJet", PrinterConnectivityStatus.OFFLINE))
        viewModel.addPrinter(PrinterInfo("Canon", "E510", PrinterConnectivityStatus.ONLINE))
        withContext(Dispatchers.Main) {
            callback.populateSpinner()
        }
    }

    fun addFile(activity: Activity, uri: Uri) {
        FileLoader.resolveFileDetails(viewModel, activity, uri)
    }

    suspend fun work(printer: PrinterInfo) {
        while (true) {
            if (printer.fileQueue.size == 0)
                continue

            printer.workStarted = true
            val item = printer.fileQueue.element()
            when (item.status) {
                JobStatus.DONE -> {
                    delay(5000)
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

    private suspend fun waitForPrintToFinish(size: Long) {
        val timeToPrint = PREDEFINED_TIME_TO_PRINT + size.div(100)
        delay(timeToPrint)
    }

    companion object {
        private const val PREDEFINED_TIME_TO_PRINT = 4000 //ms
    }
}