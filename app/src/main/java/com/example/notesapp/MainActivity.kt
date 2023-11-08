package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewsAndAdapter()
        setUpClickListener()
    }

    private val sharedPref by lazy {
        NotesDatabase(this)
    }
    private val adapter: NotesAdapter by lazy {
        NotesAdapter()
    }

    private fun setUpClickListener() {
        binding.addFBtn.setOnClickListener {
            Log.d("Baha", "addFBtn")
            startActivity((Intent(this@MainActivity, AddNoteActivity::class.java)))
        }
        binding.deleteCard.setOnClickListener {
            showConfirmDialog()
        }
    }

    private fun showConfirmDialog() {
        val alterDialog = MaterialAlertDialogBuilder(this)
        alterDialog.setMessage("de")
        alterDialog.setPositiveButton("yes") { dialog, _ ->
            deleteAllNotes()
            dialog.dismiss()
        }
        alterDialog.setNegativeButton("no") { dialog, _ ->
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
            binding.imageView.visibility = View.GONE
            binding.itemsRv.visibility = View.VISIBLE
            adapter.updateList(lisOfNotes)
            binding.itemsRv.adapter = adapter
        }
    }

    private fun deleteAllNotes() {
        sharedPref.deleteAllNotes()
        adapter.updateList(emptyList())
        binding.itemsRv.visibility = View.GONE
        binding.imageView.visibility = View.VISIBLE
    }
}