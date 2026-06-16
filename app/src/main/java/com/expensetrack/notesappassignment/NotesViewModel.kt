package com.expensetrack.notesappassignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expensetrack.notesappassignment.data.NoteEntity
import com.expensetrack.notesappassignment.data.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _notes = MutableLiveData<List<NoteEntity>>()
    val notes: LiveData<List<NoteEntity>> get() = _notes

    init {
        viewModelScope.launch {
            notesRepository.getAllNotes().collect { list ->
                _notes.value = list
            }
        }
    }

    fun addNote(note: NoteEntity) {
        viewModelScope.launch {
            notesRepository.insertNote(note)
        }
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            notesRepository.updateNote(note)
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
        }
    }
}




