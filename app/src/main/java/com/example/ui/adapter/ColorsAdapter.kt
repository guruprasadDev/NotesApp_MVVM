package com.example.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ItemColorBinding
import com.example.uitls.NoteColor

class ColorsAdapter : RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder>() {
    private var colorsList = NoteColor.values()
    var colorSelectedListener: ColorSelectedListener? = null
    var selectedItemPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
        val binding = ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        holder.bind(position)
        updateTick(holder.binding, position)
    }

    inner class ColorsViewHolder(val binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {
                containerItemColor.setOnClickListener {
                    colorSelectedListener?.onItemSelected(colorsList[position])
                    selectedItemPosition = position
                    notifyDataSetChanged()
                }
                viewBg.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor(colorsList[position].name))
            }
        }
    }

    private fun updateTick(binding: ItemColorBinding, position: Int) {
        if (selectedItemPosition == position) {
            binding.imgTick.visibility = VISIBLE
        } else {
            binding.imgTick.visibility = GONE
        }
    }

    override fun getItemCount() = colorsList.size

    fun setListener(colorSelectedListener: ColorSelectedListener) {
        this.colorSelectedListener = colorSelectedListener
    }
}

interface ColorSelectedListener {
    fun onItemSelected(noteColor: NoteColor)
}
