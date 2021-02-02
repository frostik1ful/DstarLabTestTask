package com.example.notes.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.notes.data.converter.DateConverter
import java.util.*
import kotlin.reflect.KClass

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var text: String,
    var date: Date
)