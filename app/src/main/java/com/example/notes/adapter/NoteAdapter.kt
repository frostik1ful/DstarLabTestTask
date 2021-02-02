package com.example.notes.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.entity.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    lateinit var onNoteClickListener: OnNoteClickListener
    val notesList = ArrayList<Note>()
    var selectingNotes = false
    val noteViews = ArrayList<View>()
    val checkBoxStateArray = SparseBooleanArray()
    val notesIdToDelete = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.note_view_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.checkBox.isChecked = checkBoxStateArray.get(position, false)
        val note: Note = notesList[position]
        holder.titleView.text = note.title
        holder.textView.text = note.text
        holder.dateView.text = note.date.toString()
        holder.noteId = note.id
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    interface OnNoteClickListener {
        fun onNoteClick(noteView: View, noteId: Int, position: Int)
        fun onNoteLongClick(noteView: View, noteId: Int, position: Int)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.titleView)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val dateView: TextView = itemView.findViewById(R.id.dateView)
        val checkBox: CheckBox = itemView.findViewById(R.id.noteSelectCheckBox)
        var noteId: Int = 0


        init {
            noteViews.add(itemView)
            checkBox.setOnClickListener {
                val check = !checkBoxStateArray.get(adapterPosition, false)
                checkBox.isChecked = check
                checkBoxStateArray.put(adapterPosition, check)
                if (checkBox.isChecked) {
                    notesIdToDelete.add(noteId)
                } else {
                    notesIdToDelete.remove(noteId)
                }
            }
            if (selectingNotes) {
                checkBox.visibility = View.VISIBLE
            }
            itemView.setOnClickListener {
                onNoteClickListener?.let {
                    it.onNoteClick(itemView, noteId, adapterPosition)
                }
            }
            itemView.setOnLongClickListener {
                onNoteClickListener?.let {
                    onNoteClickListener.onNoteLongClick(itemView, noteId, adapterPosition)
                }
                false
            }
        }

    }


}