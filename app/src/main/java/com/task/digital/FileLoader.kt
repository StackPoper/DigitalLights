package com.task.digital

import android.app.Activity
import android.content.ContentProvider
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.task.digital.data.ItemInfo
import com.task.digital.data.JobStatus

/**
 * Utility object to load a file from the local storage.
 */
object FileLoader {
    // Request code for selecting a PDF document.
    const val PICK_PDF_FILE = 33

    /**
     * Start the system file picker with an [Intent] with explicitly defined mime types.
     */
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

    /**
     * Get the chosen [ItemInfo] from the [ContentProvider].
     */
    fun resolveFileDetails(activity: Activity, uri: Uri): ItemInfo? {
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
                val nameArray = displayName.split('.')
                return ItemInfo(nameArray[0], nameArray[1], JobStatus.WAITING, size)
            }
        }
        return null
    }
}