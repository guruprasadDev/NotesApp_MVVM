package com.example.ViewModel

import androidx.lifecycle.ViewModel

class AddNotesViewModel : ViewModel() {
    fun validateNote(title: String, noteMessage: String): Boolean {
        return title.isNotEmpty() || noteMessage.isNotEmpty()
    }
}
