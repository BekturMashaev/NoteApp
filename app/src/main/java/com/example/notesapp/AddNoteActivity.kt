package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.google.android.material.snackbar.Snackbar

class AddNoteActivity : AppCompatActivity() {
    private val binding: ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }
    private val sharedPref by lazy {
        NotesDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
    }

    private fun saveNotes() = binding.apply {
        if (titleEt.text?.isNotEmpty() == true && descriptionEt.text?.isNotEmpty() == true) {
            showToastManager("ваша машина сохранена")
            sharedPref.saveNotes(
                NotesModel(
                    notesTitle = binding.titleEt.text.toString(),
                    notesDescription = binding.descriptionEt.text.toString(),
                )
            )
            startActivity(Intent(this@AddNoteActivity, MainActivity::class.java))
        } else showToastManager("не должны быть пустыми")
    }

    private fun showToastManager(massage: String) {
        Snackbar.make(
            binding.root,
            massage,
            Snackbar.LENGTH_SHORT,
        ).show()
    }
}