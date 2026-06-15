package com.expensetrack.notesappassignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetrack.notesappassignment.data.NoteEntity
import com.expensetrack.notesappassignment.data.NotesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesDao: NotesDao
) : ViewModel() {

    private val _notes = MutableLiveData<List<NoteEntity>>()
    val notes: LiveData<List<NoteEntity>> get() = _notes

    init {
        viewModelScope.launch {
            notesDao.getAllNotes().collect { list ->
                _notes.value = list
            }
        }
    }

    fun addNote(note: NoteEntity) {
        viewModelScope.launch {
            notesDao.insertNote(note)
        }
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            notesDao.updateNote(note)
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            notesDao.deleteNote(note)
        }
    }
}




