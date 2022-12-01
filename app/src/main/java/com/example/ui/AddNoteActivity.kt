package com.example.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.viewModel.AddNotesViewModel
import com.example.data.Note
import com.example.extensions.showToast
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.example.uitls.CommonUtils.getSimpleDateFormat
import java.util.Date
import kotlin.Exception

class AddNoteActivity : AppCompatActivity() {
    private var binding: ActivityAddNoteBinding? = null
    private var viewModel: AddNotesViewModel? = null
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModel = ViewModelProvider(this).get(AddNotesViewModel::class.java)

        initView()
        initListener()
    }

    private fun initView() {
        val noteToUpdate = noteToUpdate()
        if (noteToUpdate != null) {
            binding?.apply {
                editName.setText(noteToUpdate.title)
                editNote.setText(noteToUpdate.noteDec)
            }
            isUpdate = true
        }
    }

    private fun noteToUpdate(): Note? {
        return try {
            intent.getSerializableExtra("current_note") as Note
        } catch (e: Exception) {
            println("$e")
            null
        }
    }

    private fun initListener() {
        binding?.apply {
            imgCheck.setOnClickListener {
                val title = editName.text.toString()
                val noteMessage = editNote.text.toString()
                viewModel?.apply {
                    if (validateNote(title, noteMessage)) {
                        val newNote = Note(title = title, noteDec = noteMessage)
                        if (isUpdate) {
                            noteToUpdate()?.id?.let { noteId ->
                                newNote.id = noteId
                            }
                        }
                        setResultPostNoteUpdate(prepareNote(newNote))
                    } else {
                        showToast("Please enter some data")
                        return@setOnClickListener
                    }
                }
            }

            imgBackArrow.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun prepareNote(newNote: Note): Note {
        return newNote.copy(date = getSimpleDateFormat().format(Date()))
    }

    private fun setResultPostNoteUpdate(newNote: Note) {
        val intent = Intent()
        intent.putExtra("isUpdate", isUpdate)
        intent.putExtra("note", newNote)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
