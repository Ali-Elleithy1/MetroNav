package com.example.metronav.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.metronav.R
import com.example.metronav.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding:FragmentAboutBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_about,container,false)
        return binding.root
    }
}