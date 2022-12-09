package com.example.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uitls.NoteColor

class AddNotesViewModel : ViewModel() {
    val noteColorLiveData = MutableLiveData<NoteColor>()

    fun onColorChanged(noteColor: NoteColor) {
        noteColorLiveData.value = noteColor
    }

    fun validateNote(title: String, noteMessage: String): Boolean {
        return title.isNotEmpty() || noteMessage.isNotEmpty()
    }
}
