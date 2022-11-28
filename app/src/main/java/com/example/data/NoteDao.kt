package com.example.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("Select * from notes_table order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("UPDATE notes_table SET title= :title,note =:note WHERE id =:id")
    suspend fun update(id: Int?, title: String?, note: String?)
}