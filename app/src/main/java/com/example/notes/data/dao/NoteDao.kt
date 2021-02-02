package com.example.notes.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.data.entity.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: Note)

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY title")
    fun getAllNotesSortedByTitle(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY date")
    fun getAllNotesSortedByDate(): LiveData<List<Note>>

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM note_table WHERE id = :id")
    fun deleteNoteById(id: Int)
}