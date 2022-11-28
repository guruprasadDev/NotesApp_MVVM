package com.example.Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.data.Note
import com.example.notesapp.R
import kotlin.random.Random

class NotesAdapter(private val context: Context, val listener: NotesItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val NotesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = NotesList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true
        holder.Note_tv.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true
        holder.notes_layout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomColor(),
                null
            )
        )
        holder.notes_layout.setOnClickListener {
            listener.onItemClicked(NotesList[holder.adapterPosition])
        }
        holder.notes_layout.setOnLongClickListener {
            listener.onLongItemClicked(NotesList[holder.adapterPosition], holder.notes_layout)
            true
        }
    }

    override fun getItemCount(): Int {
        return NotesList.size
    }

    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)

        NotesList.clear()
        NotesList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search: String) {
        NotesList.clear()

        for (item in fullList) {
            if (item.title?.lowercase()
                    ?.contains(search.lowercase()) == true || item.note?.lowercase()
                    ?.contains(search.lowercase()) == true
            ) {
                NotesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.Aqua)
        list.add(R.color.green)
        list.add(R.color.Blue)
        list.add(R.color.Red)
        list.add(R.color.Fuchsia)
        list.add(R.color.Yellow)
        list.add(R.color.Navy)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val Note_tv = itemView.findViewById<TextView>(R.id.tv_Note)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
    }

    interface NotesItemClickListener {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}