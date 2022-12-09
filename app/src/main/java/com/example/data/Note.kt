package com.example.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.uitls.NoteColor

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "noteDescription") val noteDescription: String = "",
    @ColumnInfo(name = "date") val date: String = "",
    @ColumnInfo(name = "color") var color: String
) : java.io.Serializable
