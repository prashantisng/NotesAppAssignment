package com.expensetrack.notesappassignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.expensetrack.notesappassignment.data.NoteEntity
import com.expensetrack.notesappassignment.databinding.NotesLayoutBinding
import java.text.DateFormat
import java.util.Date

class NotesAdapter(
    private val onItemClicked: (NoteEntity) -> Unit,
    private val onDeleteClicked: (NoteEntity) -> Unit
) : ListAdapter<NoteEntity, NotesAdapter.UserViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = NotesLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note, onItemClicked, onDeleteClicked)
    }

    class UserViewHolder(val layoutBinding: NotesLayoutBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {

        fun bind(
            note: NoteEntity,
            onItemClicked: (NoteEntity) -> Unit,
            onDeleteClicked: (NoteEntity) -> Unit
        ) {
            layoutBinding.title.text = note.title
            layoutBinding.content.text = note.content

            val formattedDate = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.SHORT
            ).format(Date(note.timestamp))
            layoutBinding.timeStamp.text = formattedDate

            itemView.setOnClickListener {
                onItemClicked(note)
            }
            layoutBinding.ibDelete.setOnClickListener {
                onDeleteClicked(note)
            }
        }
    }
}

private class UserDiffCallBack : DiffUtil.ItemCallback<NoteEntity>() {
    override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean =
        oldItem == newItem
}