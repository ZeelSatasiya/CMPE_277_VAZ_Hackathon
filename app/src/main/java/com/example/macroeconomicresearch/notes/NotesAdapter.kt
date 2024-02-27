package com.example.macroeconomicresearch.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.macroeconomicresearch.databinding.ItemNoteBinding
import com.example.macroeconomicresearch.db.Database
import com.example.macroeconomicresearch.db.Note

class NotesAdapter(val notes : ArrayList<Note> )  : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {


    private val helper  by lazy {
        DiffUtilHelper(
            NoteDiffUtil,
            this,
            0)
    }

    private object NoteDiffUtil : DiffUtil.ItemCallback<Note>()
    {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.note == newItem.note
        }

    }

    fun setNotes(notes : List<Note>)
    {
        this.notes.clear()
        this.notes.addAll(notes)
        helper.submitList(notes)
    }

     inner class NoteViewHolder(val binding : ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
       return NoteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return notes.count()
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.text.text = notes[position].note

        holder.binding.delete.setOnClickListener {
            Database.getInstance(holder.itemView.context).NoteDao().removeNote(notes[position])
        }
    }
}