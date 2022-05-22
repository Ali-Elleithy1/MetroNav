package com.example.metronav.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users_table", indices = [Index(value = ["email"],unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "fname")
    var fName: String ="",

    @ColumnInfo(name = "lname")
    var lName: String ="",

    @ColumnInfo(name = "email")
    var email: String = "",

    @ColumnInfo(name = "password")
    var password: String = ""
)