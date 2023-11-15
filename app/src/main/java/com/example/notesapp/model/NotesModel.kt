package com.example.notesapp.model

import java.io.Serializable
import java.util.UUID

data class NotesModel(
    val notesId:String=UUID.randomUUID().toString(),
    val notesTitle: String,
    val notesDescription: String
):Serializable
