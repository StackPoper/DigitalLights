package com.task.digital

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.task.digital.data.Controller
import com.task.digital.service.SpoolerService
import com.task.digital.ui.MainFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var fragment: MainFragment
    private lateinit var service: SpoolerService
    private lateinit var connection: ServiceConnection

    override fun onStart() {
        super.onStart()
        // Bind to LocalService
        connection = object : ServiceConnection {
            override fun onServiceConnected(className: ComponentName, srvs: IBinder) {
                val binder = srvs as SpoolerService.ServiceBinder
                service = binder.getService()

                lifecycleScope.launch(Dispatchers.Default) {
                    service.work(fragment)
                }
            }
            override fun onServiceDisconnected(arg0: ComponentName) {}
        }

        Intent(this, SpoolerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment = MainFragment.newInstance()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }
    }
}