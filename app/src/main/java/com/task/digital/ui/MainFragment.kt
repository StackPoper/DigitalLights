package com.task.digital.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.task.digital.FileLoader
import com.task.digital.MainViewModel
import com.task.digital.R
import com.task.digital.databinding.FragmentMainBinding
import com.task.digital.service.IServiceCallback
import com.task.digital.service.SpoolerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener, IServiceCallback {

    val viewModel: MainViewModel by viewModels()
    var service: SpoolerService? = null
    private lateinit var connection: ServiceConnection
    private lateinit var binding: ViewDataBinding
    private lateinit var spinner: Spinner
    private lateinit var addBtn: AppCompatButton

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unbindService(connection)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the MainViewModel
        (binding as FragmentMainBinding).viewModel = viewModel

        // Sets the adapter of the RecyclerView
        (binding as FragmentMainBinding).items.adapter = ItemsListAdapter()

        addBtn = binding.root.findViewById(R.id.add_file_btn)
        addBtn.setOnClickListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = binding.root.findViewById(R.id.printers_spinner)
        spinner.onItemSelectedListener = this
        // Bind to LocalService
        connection = object : ServiceConnection {
            override fun onServiceConnected(className: ComponentName, srvs: IBinder) {
                val binder = srvs as SpoolerService.ServiceBinder
                service = binder.getService(viewModel)
                lifecycleScope.launch(Dispatchers.Default) {
                    service?.initPrintersList(this@MainFragment)
                }
            }
            override fun onServiceDisconnected(arg0: ComponentName) {}
        }

        Intent(activity, SpoolerService::class.java).also { intent ->
            activity?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.currentPrinterChoice = position
        viewModel.setItems()
        val currentPrinter = viewModel.getCurrentPrinter()
        addBtn.isEnabled = currentPrinter.isOnline()
        if (currentPrinter.isOnline() && !currentPrinter.workStarted)
            lifecycleScope.launch(Dispatchers.Default) {
                service!!.work(viewModel.getCurrentPrinter())
            }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(activity, "NOTHING SELECTED", Toast.LENGTH_LONG)
            .show()
    }

    override fun onClick(v: View?) {
        activity?.let {
            FileLoader.openFile(it)
        }
    }

    override fun updateUI() {
        spinner.adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            viewModel.printers.toArray()
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }
}