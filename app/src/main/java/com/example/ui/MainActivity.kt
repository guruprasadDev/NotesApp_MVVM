package com.example.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ui.adapter.NotesAdapter
import com.example.viewModel.MainViewModel
import com.example.data.Note
import com.example.notesapp.R
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.ui.AddNoteActivity.Companion.EXTRA_CURRENT_NOTE
import com.example.ui.AddNoteActivity.Companion.EXTRA_IS_UPDATE
import com.example.ui.AddNoteActivity.Companion.EXTRA_NOTE

class MainActivity : AppCompatActivity(), NotesAdapter.NotesItemClickListener,
    PopupMenu.OnMenuItemClickListener {
    private var binding: ActivityMainBinding? = null
    private var viewModel: MainViewModel? = null
    private var notesAdapter: NotesAdapter? = null
    private var selectedNote: Note? = null

    private val getActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val note = result.data?.getSerializableExtra(EXTRA_NOTE) as? Note
                val isUpdate = result.data?.getBooleanExtra(EXTRA_IS_UPDATE, false) ?: false
                if (note != null) {
                    if (isUpdate) {
                        viewModel?.updateNote(note)
                    } else {
                        viewModel?.insertNote(note)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MainViewModel::class.java]

        initUI()
        initObserver()
        initListener()
    }

    private fun initUI() {
        notesAdapter = NotesAdapter(this)
        binding?.recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
            adapter = notesAdapter
        }
    }

    private fun initObserver() {
        viewModel?.allNotes?.observe(this) { notesList ->
            notesList.let {
                viewModel?.notesList?.clear()
                viewModel?.notesList?.addAll(notesList)
                notesAdapter?.updateList(notesList)
            }
        }
    }

    private fun initListener() {
        binding?.fbAdd?.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            getActivityLauncher.launch(intent)
        }

        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterNotes(newText)
                }
                return true
            }
        })
    }

    private fun filterNotes(searchTerm: String) {
        viewModel?.let {
            notesAdapter?.updateList(it.filterNotes(searchTerm))
        }
    }

    override fun onItemClicked(note: Note) {
        val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
        intent.putExtra(EXTRA_CURRENT_NOTE, note)
        getActivityLauncher.launch(intent)
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectedNote = note
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {
        val popup = PopupMenu(this, cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.pop_menu)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.delete_note) {
            selectedNote?.let { viewModel?.deleteNote(it) }
            true
        } else {
            false
        }
    }
}
