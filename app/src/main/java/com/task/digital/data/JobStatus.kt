package com.task.digital.data

import com.task.digital.R

enum class JobStatus {
    WAITING,
    IN_PROGRESS,
    DONE;

    fun get() =
        when(this) {
            WAITING -> R.string.job_status_waiting
            IN_PROGRESS -> R.string.job_status_in_progress
            DONE -> R.string.job_status_done
    }
}