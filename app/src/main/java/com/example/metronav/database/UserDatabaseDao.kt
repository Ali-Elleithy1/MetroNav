package com.example.metronav.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDatabaseDao
{
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM users_table WHERE userId = :key")
    fun get(key: Long)

    @Query("SELECT * FROM users_table WHERE email = :email AND password = :password")
    fun find(email: String, password: String)

    @Query("DELETE FROM users_table")
    fun clear()

    @Query("SELECT * FROM users_table")
    fun getAllUsers(): LiveData<User>
}