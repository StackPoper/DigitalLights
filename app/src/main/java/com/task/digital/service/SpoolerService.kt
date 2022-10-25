package com.task.digital.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.task.digital.data.Controller
import com.task.digital.data.JobStatus
import com.task.digital.ui.MainFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpoolerService : Service() {
    private val binder = ServiceBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    suspend fun work(fragment: MainFragment) {
        while (Controller.currentFileQueueHasElements()) {
            when (Controller.getCurrentHeadStatus()) {
                JobStatus.DONE ->
                    Controller.pollCurrentHeadFromQueue()
                JobStatus.IN_PROGRESS -> {
                    waitForPrintToFinish()
                    Controller.changeCurrentHeadStatus(JobStatus.DONE)
                }
                JobStatus.WAITING ->
                    Controller.changeCurrentHeadStatus(JobStatus.IN_PROGRESS)
            }
            withContext(Dispatchers.Main) {
                fragment.viewModel.setItems(Controller.current)
            }
        }
    }

    inner class ServiceBinder : Binder() {
        fun getService(): SpoolerService =
            this@SpoolerService
    }

    private fun waitForPrintToFinish() {
        val timeToPrint = PREDEFINED_TIME_TO_PRINT + Controller.getCurrentHeadSize().div(100)
        Thread.sleep(timeToPrint)
    }

    companion object {
        private const val PREDEFINED_TIME_TO_PRINT = 4000 //ms
    }
}