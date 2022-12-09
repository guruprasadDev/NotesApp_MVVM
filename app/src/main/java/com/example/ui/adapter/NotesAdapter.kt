package com.example.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.data.Note
import com.example.notesapp.databinding.ListItemBinding

class NotesAdapter(private val listener: NotesItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private lateinit var binding: ListItemBinding
    private val notesList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.bind(currentNote)
    }

    private fun updateBackgroundColor(color: String) {
        val noteBgColor = Color.parseColor(color)
        binding.cardLayout.setBackgroundColor(noteBgColor)
    }

    override fun getItemCount() = notesList.size

    fun updateList(newList: List<Note>) {
        notesList.clear()
        notesList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentNote: Note) {
            with(binding) {
                tvTitle.text = currentNote.title
                tvNote.text = currentNote.noteDescription
                tvDate.text = currentNote.date
                updateBackgroundColor(currentNote.color)

                cardLayout.setOnClickListener {
                    listener.onItemClicked(notesList[adapterPosition])
                }
                cardLayout.setOnLongClickListener {
                    listener.onLongItemClicked(
                        notesList[adapterPosition],
                        cardLayout
                    )
                    true
                }
            }
        }
    }

    interface NotesItemClickListener {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}
