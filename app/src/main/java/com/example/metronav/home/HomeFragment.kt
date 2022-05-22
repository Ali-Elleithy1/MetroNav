package com.example.metronav.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.metronav.R
import com.example.metronav.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var viewModel:HomeViewModel
    private lateinit var arrayAdapter: ArrayAdapter<String>

    var list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding:FragmentHomeBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home,container,false)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        for (item in viewModel.stations)
        {
            list.add(item.name)
        }

        //Create Array Adapter for AutoCompleteTextView
        arrayAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1,list)
        binding.fromACTV.setAdapter(arrayAdapter)
        binding.toACTV.setAdapter(arrayAdapter)

        setHasOptionsMenu(true)



        binding.findRouteButton.setOnClickListener {
            Log.i("fromACTV",binding.fromACTV.text.length.toString())
            viewModel.from = binding.fromACTV.text.toString()
            viewModel.to= binding.toACTV.text.toString()
            binding.resultText.text = viewModel.getResult()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }
}