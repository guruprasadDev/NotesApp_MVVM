package com.example.notesapp

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.data.Note
import com.example.notesapp.databinding.ActivityAddNoteBinding
import java.lang.Exception
import java.sql.Date

class AddNote : AppCompatActivity() {
    private lateinit var binding :ActivityAddNoteBinding
    private lateinit var note :Note
    private lateinit var old_note:Note
    var isUpdate = false

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            old_note = intent.getSerializableExtra("current_note") as Note
            binding.editName.setText(old_note.title)
            binding.editNote.setText(old_note.note)
            isUpdate = true

        }catch (e:Exception){
            e.printStackTrace()
        }
        binding.imgCheck.setOnClickListener {
            val title = binding.editNote.text.toString()
            val note = binding.editNote.text.toString()

            if(title.isEmpty()|| note.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE , d MMM yyyy MM:mm a")
                if(isUpdate){
                    this.note = Note(
                        old_note.id,title,note,formatter.format(java.util.Date())
                    )
                }else{
                    this.note = Note(
                        null,title,note,formatter.format(java.util.Date())
                    )
                }
                val intent = Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{
                Toast.makeText(this@AddNote,"Please enter some data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }
}