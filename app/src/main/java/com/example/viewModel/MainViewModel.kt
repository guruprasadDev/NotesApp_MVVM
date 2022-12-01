package com.example.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val filteredNoteList = ArrayList<Note>()

    private var _filterListLiveData: MutableLiveData<List<Note>> = MutableLiveData()
    var filterListLiveData: LiveData<List<Note>> = _filterListLiveData

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

    fun filterList(searchTerm: String) {
        filteredNoteList.clear()

        for (item in notesList) {
            if (item.title.lowercase().contains(searchTerm.lowercase())
                || item.noteDec.lowercase().contains(searchTerm.lowercase())
            ) {
                filteredNoteList.add(item)
            }
        }
        _filterListLiveData.value = filteredNoteList
    }
}