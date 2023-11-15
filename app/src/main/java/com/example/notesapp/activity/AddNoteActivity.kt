package com.example.notesapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.R
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.example.notesapp.model.NotesModel
import com.google.android.material.snackbar.Snackbar

class AddNoteActivity : AppCompatActivity() {
    private val binding: ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }
    private val sharedPref by lazy {
        NotesDatabase(this)
    }
    private val notes: NotesModel? by lazy {
        intent.extras?.getSerializable(MainActivity.NAVIGATE_TO_DETAILS) as? NotesModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (notes != null) {
            binding.titleEt.setText(notes?.notesTitle)
            binding.descriptionEt.setText(notes!!.notesDescription)
        }
        binding.saveCard.setOnClickListener {
            saveNotes()
        }
        binding.backCard.setOnClickListener {
            finish()
        }
    }

    private fun saveNotes() = binding.apply {
        if (titleEt.text?.isNotEmpty() == true && descriptionEt.text?.isNotEmpty() == true) {
            if (notes != null) {
                if (notes!!.notesTitle != titleEt.text.toString() || notes!!.notesDescription != descriptionEt.text.toString()) {
                    val model = notes!!.copy(
                        notesTitle = titleEt.text.toString(),
                        notesDescription = descriptionEt.text.toString()
                    )
                    sharedPref.updateNotes(model)
                } else finish()
            } else {
                sharedPref.saveNotes(
                    NotesModel(
                        notesTitle = binding.titleEt.text.toString(),
                        notesDescription = binding.descriptionEt.text.toString(),
                    )
                )
            }
            startActivity(Intent(this@AddNoteActivity, MainActivity::class.java))
        } else showToastManager(getString(R.string.not_empty_fields))
    }

    private fun showToastManager(massage: String) {
        Snackbar.make(
            binding.root,
            massage,
            Snackbar.LENGTH_SHORT,
        ).show()
    }
}