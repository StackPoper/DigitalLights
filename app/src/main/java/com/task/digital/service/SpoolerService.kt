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
import com.task.digital.data.ItemInfo
import com.task.digital.MainActivity
import com.task.digital.ui.MainFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * A service bound to [MainActivity] through [MainFragment]
 * that initializes the [PrinterInfo] list and manages the [ItemInfo] job [ConcurrentLinkedQueue].
 */
class SpoolerService : Service() {
    private val binder = ServiceBinder()
    private lateinit var viewModel: MainViewModel

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    /**
     * Initializes the [PrinterInfo] list asynchronously. In a real situation printers
     * will be obtained from a network.
     */
    suspend fun initPrintersList(callback: IServiceCallback) {
        viewModel.addPrinter(PrinterInfo("Samsung", "MFP550", PrinterConnectivityStatus.ONLINE))
        viewModel.addPrinter(PrinterInfo("HP", "LaserJet", PrinterConnectivityStatus.OFFLINE))
        viewModel.addPrinter(PrinterInfo("Canon", "E510", PrinterConnectivityStatus.ONLINE))
        withContext(Dispatchers.Main) {
            callback.updateUI()
        }
    }

    /**
     * Adds a new pdf or image [ItemInfo] to the corresponding [PrinterInfo] job [ConcurrentLinkedQueue].
     */
    fun addFile(activity: Activity, uri: Uri) {
        FileLoader.resolveFileDetails(activity, uri)?.let {
            viewModel.addFileToCurrentPrinterQueue(it)
        }
    }

    /**
     * Manages the job [ConcurrentLinkedQueue] asynchronously.
     */
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

    /**
     * Gets a reference to the [MainViewModel] and returns a reference to this [SpoolerService].
     */
    inner class ServiceBinder : Binder() {
        fun getService(vm: MainViewModel): SpoolerService {
            viewModel = vm
            return this@SpoolerService
        }
    }

    /**
     * Simulate printer working on a current job.
     */
    private suspend fun waitForPrintToFinish(sizeBytes: Long) {
        val timeToPrint = PREDEFINED_TIME_TO_PRINT + sizeBytes.div(100)
        delay(timeToPrint)
    }

    companion object {
        private const val PREDEFINED_TIME_TO_PRINT = 4000 //ms
    }
}