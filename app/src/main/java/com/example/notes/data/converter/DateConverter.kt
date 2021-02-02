package com.example.notes.data.converter

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(longTime: Long): Date {
        return Date(longTime)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
}