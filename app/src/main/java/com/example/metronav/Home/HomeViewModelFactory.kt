package com.example.metronav.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModelFactory (private val from:String,private val to:String):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java))
        {
            return HomeViewModel(from, to) as T
        }
        throw IllegalArgumentException("Unknown viewModel Class")
    }

}