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
    var colorsList = NoteColor.values()
    var colorSelectedListener: ColorSelectedListener? = null
    var selectedItemPosition = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
        val binding = ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        holder.binding.containerItemColor.setOnClickListener {
            colorSelectedListener?.onItemSelected(colorsList[position])
            selectedItemPosition = position
            notifyDataSetChanged()
        }

        holder.binding.viewBg.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(colorsList[position].name))

        updateTick(holder.binding, position)
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

    class ColorsViewHolder(val binding: ItemColorBinding) : RecyclerView.ViewHolder(binding.root)
}

interface ColorSelectedListener {
    fun onItemSelected(noteColor: NoteColor)
}