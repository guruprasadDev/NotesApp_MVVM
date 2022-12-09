package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val title: String = "",
    val noteDescription: String = "",
    val date: String = "",
    var color: String
) : java.io.Serializable
