package com.example.metronav.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.metronav.R
import com.example.metronav.Station
import com.example.metronav.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var viewModel:HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var arrayAdapter: ArrayAdapter<String>

    var list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding:FragmentHomeBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home,container,false)

        viewModelFactory = HomeViewModelFactory(binding.fromACTV.toString(),binding.toACTV.toString())
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)

        for (item in viewModel.stations)
        {
            list.add(item.name)
        }

        //Create Array Adapter for AutoCompleteTextView
        arrayAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1,list)
        binding.fromACTV.setAdapter(arrayAdapter)
        binding.toACTV.setAdapter(arrayAdapter)



        binding.findRouteButton.setOnClickListener {

        }

        return binding.root
    }
}