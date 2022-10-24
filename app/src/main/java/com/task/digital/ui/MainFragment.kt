package com.task.digital.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.task.digital.MainViewModel
import com.task.digital.R
import com.task.digital.data.PrinterConnectivityStatus
import com.task.digital.data.PrinterInfo
import com.task.digital.databinding.FragmentMainBinding


class MainFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val viewModel: MainViewModel by viewModels()

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the ShowsInfoViewModel
        binding.viewModel = viewModel

        // Sets the adapter of the ShowsListAdapter RecyclerView
        binding.items.adapter = ItemsListAdapter()

        val printers = arrayOf(
            PrinterInfo(activity, "Samsung", "MFP550", PrinterConnectivityStatus.ONLINE),
            PrinterInfo(activity, "HP", "LaserJet", PrinterConnectivityStatus.OFFLINE),
            PrinterInfo(activity, "Canon", "E510", PrinterConnectivityStatus.ONLINE),
        )

        val spinner = binding.root.findViewById(R.id.printers_spinner) as Spinner
        spinner.onItemSelectedListener = this

        spinner.adapter = ArrayAdapter<Any?>(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            printers
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(activity, (view as TextView).text, Toast.LENGTH_LONG)
            .show();
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(activity, "NOTHING SELECTED", Toast.LENGTH_LONG)
            .show();
    }
}