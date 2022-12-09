package com.example.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uitls.NoteColor

class AddNotesViewModel : ViewModel() {
    private val _noteColorLiveData = MutableLiveData<NoteColor>()
    val noteColorLiveData: LiveData<NoteColor>
        get() = _noteColorLiveData


    fun onColorChanged(noteColor: NoteColor) {
        _noteColorLiveData.value = noteColor
    }

    fun validateNote(title: String, noteMessage: String): Boolean {
        return title.isNotEmpty() || noteMessage.isNotEmpty()
    }
}
