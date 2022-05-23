package com.example.metronav.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.metronav.R
import com.example.metronav.database.MetroDatabase
import com.example.metronav.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)

        val application = requireNotNull(this.activity).application

        val datasource = MetroDatabase.getInstance(application).userDatabaseDao

        val viewModelFactory = LoginViewModelFactory(datasource,application)

        val viewModel = ViewModelProvider(this,viewModelFactory).get(LoginViewModel::class.java)

        binding.loginViewModel = viewModel

        binding.lifecycleOwner = this

        binding.loginButton.setOnClickListener { view ->
            viewModel.onLogin(binding.lEmailEdit.text.toString(), binding.lPasswordEdit.text.toString())
            if(viewModel.user.value?.email != null)
            {
                Toast.makeText(activity,"email: ${viewModel.user.value?.email}", Toast.LENGTH_LONG).show()
                this.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            else
            {
                Toast.makeText(activity,"Wrong Credentials",Toast.LENGTH_LONG).show()
            }
        }

        binding.signUpText.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        return binding.root
    }
}