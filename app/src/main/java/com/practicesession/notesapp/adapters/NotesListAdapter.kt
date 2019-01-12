package com.practicesession.notesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practicesession.notesapp.R
import com.practicesession.notesapp.model.Note

class NotesListAdapter(context: Context) :
    ListAdapter<Note, NotesListAdapter.NoteHolder>(DIFF_CALLBACK) {
    var context: Context? = null
    var listener: OnItemClickListener? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.content.equals(newItem.content) && oldItem.fontStyle.equals(newItem.fontStyle)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.notes_item, parent, false)
        return NoteHolder(itemView)
    }

    init {
        this.context = context
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = getItem(position)
        holder.tvContent.text = currentNote.content

        when (currentNote.fontStyle) {
            0 -> {
                val typeface = context?.let { ResourcesCompat.getFont(it, R.font.baumans) }
                holder.tvContent.typeface = typeface
            }
            1 -> {
                val typeface = context?.let { ResourcesCompat.getFont(it, R.font.catamaran) }
                holder.tvContent.typeface = typeface
            }
            2 -> {
                val typeface = context?.let { ResourcesCompat.getFont(it, R.font.droid_sans) }
                holder.tvContent.typeface = typeface
            }
            3 -> {
                val typeface = context?.let { ResourcesCompat.getFont(it, R.font.hind_guntur) }
                holder.tvContent.typeface = typeface
            }
        }

    }


    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var view = itemView
        val tvContent: TextView = itemView.findViewById(R.id.tv_content)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(note: Note) {

        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}