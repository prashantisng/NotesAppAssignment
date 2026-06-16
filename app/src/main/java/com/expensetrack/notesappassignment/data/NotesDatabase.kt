package com.expensetrack.notesappassignment.data

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}