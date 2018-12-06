package com.practicesession.notesapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.practicesession.notesapp.R
import com.practicesession.notesapp.dbhelper.NoteDBManagar
import com.practicesession.notesapp.model.Note
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listNotes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadQueryAll()

        fab_add.setOnClickListener {
            val intent = Intent(this, NewNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadQueryAll()
    }

    private fun loadQueryAll() {
        val dbManager = NoteDBManagar(this)
        val cursor = dbManager.queryAll()

        listNotes.clear()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val content = cursor.getString(cursor.getColumnIndex("Content"))
                listNotes.add(Note(id, title, content))

            } while (cursor.moveToNext())
        }

        val notesAdapter = NotesAdapter(this, listNotes)
        lv_notes.emptyView = empty_note_view
        lv_notes.adapter = notesAdapter
    }

    inner class NotesAdapter : BaseAdapter {
        private var notesList = ArrayList<Note>()
        private var context: Context? = null
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return notesList[position]
        }

        override fun getCount(): Int {
            return notesList.size
        }


        constructor(context: Context, notesList: ArrayList<Note>) : super() {
            this.notesList = notesList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View?
            val vh: ViewHolder
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.notes_item, parent, false)
                vh = ViewHolder(view)
                view.tag = vh
                Log.i("JSA", "set Tag for ViewHolder, position: $position")

            } else {
                view = convertView
                vh = view.tag as ViewHolder
            }

            val mNote = notesList[position]

            vh.tvTitle!!.text = mNote.title
            vh.tvContent!!.text = mNote.content

            vh.itemView?.setOnClickListener {
                updateNote(mNote)

            }
            vh.ivDelete?.setOnClickListener {
                val dbManager = NoteDBManagar(this.context!!)
                val selectionArgs = arrayOf(mNote.id.toString())
                dbManager.delete("Id=?", selectionArgs)
                loadQueryAll()
                Toasty.success(this@MainActivity, "Deleted Successfully", Toast.LENGTH_LONG).show()
            }
            return view!!
        }

        private fun updateNote(note: Note) {
            val intent = Intent(context, NewNoteActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("MainActId", note.id!!)
            bundle.putString("MainActTitle", note.title)
            bundle.putString("MainActContent", note.content)
            intent.putExtras(bundle)
            startActivity(intent)

        }


    }

    private class ViewHolder(view: View?) {

        var itemView: View? = view
        var tvTitle: TextView? = null
        var tvContent: TextView? = null
        var ivDelete: ImageView? = null

        init {
            this.tvTitle = itemView?.findViewById(R.id.tv_title)
            this.tvContent = itemView?.findViewById(R.id.tv_content)
            this.ivDelete = itemView?.findViewById(R.id.ivDelete)

        }


    }
}
