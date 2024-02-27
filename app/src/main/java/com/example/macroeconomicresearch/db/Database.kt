package com.example.macroeconomicresearch.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
@androidx.room.Database(entities = [Note::class],
    version = 1,
    exportSchema = false)
abstract class Database : RoomDatabase()
{
    abstract fun NoteDao() : NotesDao

    companion object
    {
        private const val databaseName = "notes-db"


        @Volatile private var INSTANCE: Database? = null

        fun getInstance(context: Context) : Database =
            INSTANCE ?: synchronized(this) { INSTANCE ?:buildDatabase(context).also { INSTANCE = it } }

        private fun buildDatabase(context: Context): Database {
            return Room.databaseBuilder(context, Database::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }

    }

}