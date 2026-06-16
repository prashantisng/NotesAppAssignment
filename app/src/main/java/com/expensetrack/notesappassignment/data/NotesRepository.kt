package com.expensetrack.notesappassignment.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val notesDao: NotesDao
) {
    fun getAllNotes(): Flow<List<NoteEntity>> = notesDao.getAllNotes()

    suspend fun insertNote(note: NoteEntity) = notesDao.insertNote(note)

    suspend fun updateNote(note: NoteEntity) = notesDao.updateNote(note)

    suspend fun deleteNote(note: NoteEntity) = notesDao.deleteNote(note)
}
