package com.example.metronav.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.metronav.R
import com.example.metronav.database.MetroDatabase
import com.example.metronav.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentRegistrationBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_registration,container,false)

        val application = requireNotNull(this.activity).application

        val dataSource = MetroDatabase.getInstance(application).userDatabaseDao

        val viewModelFactory = RegistrationViewModelFactory(dataSource,application)

        val viewModel = ViewModelProvider(this,viewModelFactory).get(RegistrationViewModel::class.java)

        binding.registrationViewModel = viewModel

        binding.lifecycleOwner = this

        binding.registerButton.setOnClickListener { view ->
            viewModel.onRegister(binding.fnameEdit.text.toString(), binding.lnameEdit.text.toString(), binding.rEmailEdit.text.toString(), binding.rPasswordEdit.text.toString())
            view.findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        return binding.root
    }
}