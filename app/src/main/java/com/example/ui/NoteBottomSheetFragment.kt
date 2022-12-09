package com.example.ui

import android.app.Dialog
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.databinding.FragmentNotesBottomSheetBinding
import com.example.ui.adapter.ColorSelectedListener
import com.example.ui.adapter.ColorsAdapter
import com.example.uitls.NoteColor
import com.example.viewModel.AddNotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NoteBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding: FragmentNotesBottomSheetBinding? = null
    private var colorsAdapter: ColorsAdapter? = null
    private lateinit var viewModel: AddNotesViewModel

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        binding = FragmentNotesBottomSheetBinding.inflate(LayoutInflater.from(context))
        binding?.root?.let { dialog.setContentView(it) }
        viewModel = ViewModelProvider(requireActivity()).get(AddNotesViewModel::class.java)

        initUi()
        initListener()
    }

    private fun initUi() {
        colorsAdapter = ColorsAdapter()
        binding?.rvColor?.adapter = colorsAdapter
    }

    private fun initListener() {
        colorsAdapter?.setListener(object : ColorSelectedListener {
            override fun onItemSelected(noteColor: NoteColor) {
                viewModel.onColorChanged(noteColor)
            }
        })
    }
}
