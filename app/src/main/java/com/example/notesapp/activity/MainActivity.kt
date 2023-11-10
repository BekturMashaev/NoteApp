package com.example.notesapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.R
import com.example.notesapp.adapter.NotesAdapter
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val sharedPref by lazy {
        NotesDatabase(this)
    }
    private val adapter: NotesAdapter by lazy {
        NotesAdapter(onDeleteNoteClick = { index ->
            sharedPref.deleteNoteByIndex(index)
            setUpViewsAndAdapter()
        }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpViewsAndAdapter()
        setUpClickListener()
    }


    private fun setUpClickListener() = binding.apply {
        addNoteFbtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNoteActivity::class.java))
        }
        deleteCard.setOnClickListener {
            val listOfNotes = sharedPref.getAllNotes()
            if (listOfNotes.isNotEmpty()) showConfirmDialog()
            else showToastManager(getString(R.string.already_empty_field))
        }
    }


    private fun showConfirmDialog() {
        val alterDialog = MaterialAlertDialogBuilder(this)
        alterDialog.setMessage(getString(R.string.really_want_to_delete))
        alterDialog.setPositiveButton(getString(R.string.yes)) { dialog, _ ->
            deleteAllNotes()
            dialog.dismiss()
        }
        alterDialog.setNegativeButton(getString(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }
        alterDialog.create().show()
    }

    private fun showToastManager(massage: String) {
        Snackbar.make(
            binding.root,
            massage,
            Snackbar.LENGTH_SHORT,
        ).show()
    }

    private fun setUpViewsAndAdapter() {
        val lisOfNotes = sharedPref.getAllNotes()
        if (lisOfNotes.isNotEmpty()) {
            binding.emptyImg.visibility = View.GONE
            binding.notesRv.visibility = View.VISIBLE
            adapter.updateList(lisOfNotes)
            binding.notesRv.adapter = adapter
        }
    }

    private fun deleteAllNotes() {
        sharedPref.deleteAllNotes()
        adapter.updateList(emptyList())
        binding.emptyImg.visibility = View.GONE
        binding.notesRv.visibility = View.VISIBLE
    }
}