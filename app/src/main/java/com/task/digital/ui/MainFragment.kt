package com.task.digital.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.task.digital.MainViewModel
import com.task.digital.R
import com.task.digital.data.Controller
import com.task.digital.databinding.FragmentMainBinding


class MainFragment : Fragment(), AdapterView.OnItemSelectedListener {

    val viewModel: MainViewModel by viewModels()

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

        // Giving the binding access to the MainViewModel
        binding.viewModel = viewModel

        // Sets the adapter of the RecyclerView
        binding.items.adapter = ItemsListAdapter()

        val spinner = binding.root.findViewById(R.id.printers_spinner) as Spinner
        spinner.onItemSelectedListener = this

        spinner.adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            Controller.printers.toArray()
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =
        viewModel.setItems(position)

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(activity, "NOTHING SELECTED", Toast.LENGTH_LONG)
            .show()
    }
}