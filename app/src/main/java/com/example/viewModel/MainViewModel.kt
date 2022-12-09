package com.example.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.data.Note
import com.example.data.NoteDatabase
import com.example.data.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: NotesRepository
    var allNotes: LiveData<List<Note>>
    var notesList = ArrayList<Note>()

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NotesRepository(dao)
        allNotes = repository.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun filterNotes(searchTerm: String): List<Note> {
        val filteredNoteList = notesList.filter {
            (it.title.lowercase().contains(searchTerm.lowercase()))
                    || (it.noteDescription.lowercase().contains(searchTerm.lowercase()))
        }
        return filteredNoteList
    }
}
