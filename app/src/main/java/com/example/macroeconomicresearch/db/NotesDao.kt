package com.example.macroeconomicresearch.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotesDao {

    @Insert
    fun addNote(blacklist: Note)


    @Query("SELECT * FROM Note")
    fun getAllNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM Note")
    fun getAllNotesSync() : List<Note>

    @Query("DELETE FROM Note")
    fun deleteAllNotes()

    @Delete
    fun removeNote(note: Note)


    @Delete
    fun deleteNotes(songs : List<Note>)
}