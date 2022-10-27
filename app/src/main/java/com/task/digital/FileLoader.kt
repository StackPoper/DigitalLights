package com.task.digital

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log

object FileLoader {
    // Request code for selecting a PDF document.
    const val PICK_PDF_FILE = 33

    fun openFile(activity: Activity) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            val extraMimeTypes = arrayOf("application/pdf", "image/*")
            putExtra(Intent.EXTRA_MIME_TYPES, extraMimeTypes)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }

        activity.startActivityForResult(intent, PICK_PDF_FILE)
    }

    fun resolveFileDetails(viewModel: MainViewModel, activity: Activity, uri: Uri) {
        val cursor: Cursor? = activity.contentResolver.query(
            uri, null, null, null, null, null
        )

        cursor?.use {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (it.moveToFirst()) {
                val displayName: String =
                    it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                Log.d("BindingAdapters", "Display Name: $displayName")

                val sizeIndex: Int = it.getColumnIndex(OpenableColumns.SIZE)
                val size: Long =
                    if (!it.isNull(sizeIndex))
                        it.getLong(sizeIndex)
                    else 0
                Log.d("BindingAdapters", "Size: $size")
                addFileToQueue(viewModel, displayName, size)
            }
        }
    }

    private fun addFileToQueue(viewModel: MainViewModel, displayName: String, size: Long) {
        val nameArray = displayName.split('.')
        viewModel.addFileToCurrentPrinterQueue(nameArray[0], nameArray[1], size)
    }
}