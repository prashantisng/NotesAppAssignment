package com.expensetrack.notesappassignment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.expensetrack.notesappassignment.data.NoteEntity
import com.expensetrack.notesappassignment.databinding.ActivityMainBinding
import com.expensetrack.notesappassignment.databinding.DialogAddEditNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var notesAdapter: NotesAdapter
    
    private var allNotesList: List<NoteEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupSearch()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        notesAdapter = NotesAdapter(
            onItemClicked = { note -> showAddEditNoteDialog(note) },
            onDeleteClicked = { note -> showDeleteConfirmationDialog(note) }
        )
        
        binding.notesList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = notesAdapter
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterNotes(s.toString())
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener {
            showAddEditNoteDialog(null)
        }
    }

    private fun observeViewModel() {
        viewModel.notes.observe(this) { notes ->
            allNotesList = notes
            filterNotes(binding.etSearch.text.toString())
        }
    }

    private fun filterNotes(query: String) {
        val filteredList = if (query.isEmpty()) {
            allNotesList
        } else {
            allNotesList.filter { note ->
                note.title.contains(query, ignoreCase = true) || 
                note.content.contains(query, ignoreCase = true)
            }
        }
        
        notesAdapter.submitList(filteredList)
        
        if (filteredList.isEmpty()) {
            binding.layoutEmptyState.visibility = View.VISIBLE
            binding.notesList.visibility = View.GONE
        } else {
            binding.layoutEmptyState.visibility = View.GONE
            binding.notesList.visibility = View.VISIBLE
        }
    }

    private fun showAddEditNoteDialog(note: NoteEntity?) {
        val dialogBinding = DialogAddEditNoteBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        if (note != null) {
            dialogBinding.tvDialogTitle.text = "Edit Note"
            dialogBinding.etTitle.setText(note.title)
            dialogBinding.etContent.setText(note.content)
        } else {
            dialogBinding.tvDialogTitle.text = "Add Note"
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnSave.setOnClickListener {
            val title = dialogBinding.etTitle.text.toString().trim()
            val content = dialogBinding.etContent.text.toString().trim()

            if (title.isEmpty() && content.isEmpty()) {
                Toast.makeText(this, "Note cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (note != null) {
                // Update note
                val updatedNote = note.copy(
                    title = title,
                    content = content,
                    timestamp = System.currentTimeMillis()
                )
                viewModel.updateNote(updatedNote)
            } else {
                // Add note
                val newNote = NoteEntity(
                    title = title,
                    content = content,
                    timestamp = System.currentTimeMillis()
                )
                viewModel.addNote(newNote)
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDeleteConfirmationDialog(note: NoteEntity) {
        AlertDialog.Builder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Delete") { dialog, _ ->
                viewModel.deleteNote(note)
                dialog.dismiss()
                Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}