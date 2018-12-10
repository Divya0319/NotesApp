package com.practicesession.notesapp.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practicesession.notesapp.R
import com.practicesession.notesapp.activities.NewNoteActivity
import com.practicesession.notesapp.constants.BundleKeys
import com.practicesession.notesapp.helpers.ItemDeletionListener
import com.practicesession.notesapp.model.Note

class NotesListAdapter(context: Context, listener: ItemDeletionListener) :
    RecyclerView.Adapter<NotesListAdapter.NoteHolder>() {
    var context: Context? = null
    var listener: ItemDeletionListener? = null

    private var notes: List<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.notes_item, parent, false)
        return NoteHolder(itemView)
    }

    init {
        this.context = context
        this.listener = listener
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = notes[position]
        holder.tvContent.text = currentNote.content

        holder.view.setOnClickListener {
            val intent = Intent(context, NewNoteActivity::class.java)
            val bundle = Bundle()
            currentNote.id?.let { it1 -> bundle.putInt(BundleKeys.bundleId, it1) }
            bundle.putString(BundleKeys.bundleContent, holder.tvContent.text.toString())
            bundle.putInt(BundleKeys.bundleFont, currentNote.fontStyle)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
        holder.ivDelete.setOnClickListener {
            listener?.onItemDelete(currentNote)
        }
    }

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }


    override fun getItemCount() = notes.size

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var view = itemView

        val tvContent: TextView = itemView.findViewById(R.id.tv_content)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)


    }

}