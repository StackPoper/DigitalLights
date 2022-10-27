package com.task.digital

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.task.digital.ui.MainFragment

class MainActivity : AppCompatActivity() {
    private val fragment = MainFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == FileLoader.PICK_PDF_FILE && resultCode == RESULT_OK)
            resultData?.data?.let {
                fragment.service?.addFile(this, it)
            }
    }
}