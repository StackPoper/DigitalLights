package com.task.digital

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.task.digital.ui.MainFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                FileLoader.resolveFileDetails(fragment.viewModel, this, it)
                lifecycleScope.launch(Dispatchers.Default) {
                    fragment.service?.work(fragment.viewModel.printers[fragment.viewModel.currentPrinterChoice])
                }
            }
    }
}