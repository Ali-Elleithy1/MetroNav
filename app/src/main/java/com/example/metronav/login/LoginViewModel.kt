package com.example.metronav.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.metronav.database.User
import com.example.metronav.database.UserDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(val database: UserDatabaseDao, application: Application) : AndroidViewModel(application)
{
    private var _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    fun onClear()
    {
        viewModelScope.launch {
            clear()
        }
    }
    private suspend fun clear()
    {
        withContext(Dispatchers.IO)
        {
            database.clear()
        }
    }
    fun onLogin(email: String, password: String)
    {
        viewModelScope.launch {
            find(email,password)
        }
    }
    private suspend fun find(email: String, password: String)
    {
        withContext(Dispatchers.IO)
        {
            _user.postValue(database.find(email, password))
        }
    }
}