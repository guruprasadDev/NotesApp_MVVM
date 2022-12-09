package com.example.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.viewModel.AddNotesViewModel
import com.example.data.Note
import com.example.extensions.showToast
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.example.uitls.CommonUtils.getSimpleDateFormat
import com.example.uitls.NoteColor
import java.util.Date
import kotlin.Exception

class AddNoteActivity : AppCompatActivity() {
    private var binding: ActivityAddNoteBinding? = null
    private var viewModel: AddNotesViewModel? = null
    private var isUpdate = false
    private var noteColor: String = NoteColor.WHITE.color
    var noteBottomSheetFragment: NoteBottomSheetFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModel = ViewModelProvider(this).get(AddNotesViewModel::class.java)

        initView()
        initBottomSheet()
        initListener()
        initObserver()
    }

    private fun initView() {
        val noteToUpdate = noteToUpdate()
        if (noteToUpdate != null) {
            binding?.apply {
                editName.setText(noteToUpdate.title)
                editNote.setText(noteToUpdate.noteDescription)
                updateBackgroundColor(noteToUpdate.color)
            }
            isUpdate = true
        }
    }

    private fun initBottomSheet() {
        noteBottomSheetFragment = NoteBottomSheetFragment()
    }

    private fun updateBackgroundColor(color: String) {
        val noteBgColor = Color.parseColor(color)
        binding?.containerConstraintLayout?.setBackgroundColor(noteBgColor)
        binding?.toolbar?.setBackgroundColor(noteBgColor)
        this.noteColor = color
    }

    private fun noteToUpdate(): Note? {
        return try {
            intent.getSerializableExtra(EXTRA_CURRENT_NOTE) as Note
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
                        val newNote =
                            Note(title = title, noteDescription = noteMessage, color = noteColor)
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

            imgMore.setOnClickListener {
                noteBottomSheetFragment?.show(
                    supportFragmentManager,
                    "Note Bottom  Fragment"
                )
            }
        }
    }

    private fun initObserver() {
        viewModel?.noteColorLiveData?.observe(this, Observer { noteColor ->
            this.noteColor = noteColor.color
            updateBackgroundColor(noteColor.color)
        })
    }

    private fun prepareNote(newNote: Note): Note {
        return newNote.copy(date = getSimpleDateFormat().format(Date()))
    }

    private fun setResultPostNoteUpdate(newNote: Note) {
        val intent = Intent()
        intent.putExtra(EXTRA_IS_UPDATE, isUpdate)
        intent.putExtra(EXTRA_NOTE, newNote)
        intent.putExtra(EXTRA_COLOR, newNote.color)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object {
        const val EXTRA_IS_UPDATE = "isUpdate"
        const val EXTRA_NOTE = "note"
        const val EXTRA_CURRENT_NOTE = "currentNote"
        const val EXTRA_COLOR = "color"
    }
}
