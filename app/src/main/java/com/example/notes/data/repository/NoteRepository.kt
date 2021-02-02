package com.example.notes.data.repository

import androidx.lifecycle.LiveData
import com.example.notes.data.dao.NoteDao
import com.example.notes.data.entity.Note

class NoteRepository(private val noteDao: NoteDao) {
    fun getAllNotes(): LiveData<List<Note>> {
        return noteDao.getAllNotes()
    }

    fun getAllNotesSortedByTitle(): LiveData<List<Note>> {
        return noteDao.getAllNotesSortedByTitle()
    }

    fun getAllNotesSortedByDate(): LiveData<List<Note>> {
        return noteDao.getAllNotesSortedByDate()
    }

    fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    fun deleteNoteById(id: Int) {
        noteDao.deleteNoteById(id)
    }

}