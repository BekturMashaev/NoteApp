package com.example.notesapp.database

import android.content.Context
import com.example.notesapp.model.NotesModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

const val SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY"
const val SHARED_PREF = "Notes_SHARED_PREF"

class NotesDatabase(
    context: Context,
) {
    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE
    )

    fun getAllNotes(): List<NotesModel> {
        val gson = Gson()
        val json = sharedPreferences.getString(SHARED_PREF, null)
        val type: Type = object : TypeToken<ArrayList<NotesModel?>?>() {}.type
        val notesList = gson.fromJson<List<NotesModel>>(json, type)
        return notesList ?: emptyList()
    }

    fun saveNotes(notesModel: NotesModel) {
        val notes = getAllNotes().toMutableList()
        notes.add(0, notesModel)
        val notesGson = Gson().toJson(notes)
        sharedPreferences.edit().putString(SHARED_PREF, notesGson).apply()
    }
    fun updateNotes(notesModel: NotesModel){
        val notes = getAllNotes().toMutableList()
        notes.removeIf { notesModel.notesId==it.notesId }
        notes.add(0, notesModel)
        val notesGson = Gson().toJson(notes)
        sharedPreferences.edit().putString(SHARED_PREF, notesGson).apply()
    }
    fun deleteAllNotes() {
        sharedPreferences.edit().clear().apply()
    }

    fun deleteNoteByIndex(index: Int) {
        val getAllNotes = getAllNotes().toMutableList()
        if (index in 0 until getAllNotes.size) {
            getAllNotes.removeAt(index)
            val notesGsonString = Gson().toJson(getAllNotes)
            sharedPreferences.edit().putString(SHARED_PREF, notesGsonString).apply()
        }
    }
}