package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.data.Note
import com.example.notesapp.databinding.ListItemBinding
import com.example.uitls.randomColor

class NotesAdapter(private val listener: NotesItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val notesList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]
        with(holder.binding) {
            tvTitle.text = currentNote.title
            tvNote.text = currentNote.noteDec
            tvDate.text = currentNote.date

            cardLayout.setCardBackgroundColor(
                holder.itemView.resources.getColor(randomColor(), null)
            )
            //setting up Listeners
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

    override fun getItemCount() = notesList.size

    fun updateList(newList: List<Note>) {
        notesList.clear()
        notesList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface NotesItemClickListener {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}
