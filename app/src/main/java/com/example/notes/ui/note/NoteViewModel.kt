package com.example.notes.ui.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.data.database.NoteDatabase
import com.example.notes.data.entity.Note
import com.example.notes.data.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    var allData:LiveData<List<Note>>
    private val repository:NoteRepository
    init {
        val noteDao = NoteDatabase.getInstance(application).noteDao()
        repository = NoteRepository(noteDao)
        allData = repository.getAllNotes()

    }

    fun getAllNotesSortedByTitle(): LiveData<List<Note>> {
        return repository.getAllNotesSortedByTitle()
    }
    fun getAllNotesSortedByDate(): LiveData<List<Note>> {
        return repository.getAllNotesSortedByDate()
    }
    fun addNote(note:Note){
        viewModelScope.launch(Dispatchers.IO){
            repository.addNote(note)
        }
    }

    fun deleteNoteById(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteNoteById(id)
        }
    }
}