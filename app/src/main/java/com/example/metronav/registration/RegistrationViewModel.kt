package com.example.metronav.registration

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.metronav.database.User
import com.example.metronav.database.UserDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel (val database: UserDatabaseDao, application: Application): AndroidViewModel(application)
{
    fun onRegister(fName:String, lName:String, email:String, password:String)
    {
        viewModelScope.launch {
            val newUser = User()
            if(fName.isNotBlank() && fName.isNotEmpty() && lName.isNotBlank() && lName.isNotEmpty())
            {
                newUser.fName = fName
                newUser.lName = lName
                newUser.email = email
                newUser.password = password
                insert(newUser)
            }
        }
    }

    private suspend fun insert(user: User)
    {
        withContext(Dispatchers.IO)
        {
            database.insert(user)
        }
    }

}