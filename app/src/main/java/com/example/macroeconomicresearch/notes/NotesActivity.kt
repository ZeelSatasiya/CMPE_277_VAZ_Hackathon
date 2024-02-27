package com.example.macroeconomicresearch.notes

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.macroeconomicresearch.R
import com.example.macroeconomicresearch.databinding.ActivtiyNotesBinding
import com.example.macroeconomicresearch.db.Database
import com.example.macroeconomicresearch.inflate
import com.example.macroeconomicresearch.retrofit.DefaultScheduler


class NotesActivity : AppCompatActivity() {


    private lateinit var binding : ActivtiyNotesBinding



    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }


    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = inflate(R.layout.activtiy_notes)
        binding.graphToolbarInclude.text.text = "Notes"
        adapter = NotesAdapter(arrayListOf())
        binding.list.adapter = adapter

        Database.getInstance(this).NoteDao().getAllNotes().observe(this) {
            adapter.setNotes(it)
        }


    }
}