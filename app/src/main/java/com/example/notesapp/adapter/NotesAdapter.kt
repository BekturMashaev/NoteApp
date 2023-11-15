package com.example.notesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.NotesItemBinding
import com.example.notesapp.model.NotesModel

class NotesAdapter(
    private val onDeleteNoteClick: (index: Int) -> Unit,
    private val reportToDetails:(notes:NotesModel)->Unit
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    val notesList = mutableListOf<NotesModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(notesList: List<NotesModel>) {
        this.notesList.clear()
        this.notesList.addAll(notesList)
        notifyDataSetChanged()
    }

    inner class NotesViewHolder(
        private val binding: NotesItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notesModel: NotesModel) {
            binding.tnNotesTitle.text = notesModel.notesTitle
            binding.tnNotesDescription.text = notesModel.notesDescription
            binding.deletteBtn.setOnClickListener {
                onDeleteNoteClick.invoke(notesList.indexOf(notesModel))
            }
            binding.nameCv.setOnClickListener{
                reportToDetails.invoke(notesModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NotesItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.notes_item, parent, false
            )
        )
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int = notesList.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notesList[position])
        holder.itemView.setOnClickListener {
        }
    }
}