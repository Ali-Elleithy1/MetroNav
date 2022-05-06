package com.example.metronav.Database

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

    @Query("DELETE FROM users_table")
    fun clear()

    @Query("SELECT * FROM users_table")
    fun getAllUsers(): LiveData<User>

}