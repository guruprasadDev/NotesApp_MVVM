package com.example.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.data.Note
import com.example.notesapp.databinding.ListItemBinding
import com.example.uitls.randomColor

class NotesAdapter(private val context: Context, private val listener: NotesItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val notesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]
        with(holder) {
            binding.apply {
                tvTitle.text = currentNote.title
                tvTitle.isSelected = true
                tvNote.text = currentNote.noteDec
                tvDate.text = currentNote.date
                tvDate.isSelected = true

                cardLayout.setCardBackgroundColor(
                    itemView.resources.getColor(
                        randomColor(),
                        null
                    )
                )
                cardLayout.setOnClickListener {
                    listener.onItemClicked(notesList[holder.adapterPosition])
                }
                cardLayout.setOnLongClickListener {
                    listener.onLongItemClicked(
                        notesList[holder.adapterPosition],
                        cardLayout
                    )
                    true
                }
            }
        }

    }

    override fun getItemCount() = notesList.size

    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)
        notesList.clear()
        notesList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search: String) {
        notesList.clear()

        for (item in fullList) {
            if (item.title.lowercase()
                    .contains(search.lowercase()) || item.noteDec.lowercase()
                    .contains(search.lowercase())
            ) {
                notesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface NotesItemClickListener {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}
