package com.example.macroeconomicresearch.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Note(@PrimaryKey(autoGenerate = true) val id : Int=0, var note : String)
