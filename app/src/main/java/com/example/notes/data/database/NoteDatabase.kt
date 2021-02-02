package com.example.notes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notes.data.converter.DateConverter
import com.example.notes.data.dao.NoteDao
import com.example.notes.data.entity.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NoteDatabase {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            instance = Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_database"
            ).build()
            return instance as NoteDatabase

        }

    }

    abstract fun noteDao(): NoteDao
}