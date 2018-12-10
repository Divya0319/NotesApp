package com.practicesession.notesapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicesession.notesapp.R
import com.practicesession.notesapp.adapters.NotesListAdapter
import com.practicesession.notesapp.helpers.ItemDeletionListener
import com.practicesession.notesapp.model.Note
import com.practicesession.notesapp.model.NotesViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ItemDeletionListener {
    private var notesListAdapter: NotesListAdapter? = null

    private var mNotesViewModel: NotesViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_notes)
        notesListAdapter = NotesListAdapter(this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = notesListAdapter

        mNotesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)

        mNotesViewModel?.getAllNotes()?.observe(this, Observer { notes ->
            notes.let {
                notesListAdapter?.setNotes(it)
                if (it.isEmpty()) {
                    recyclerView.visibility = View.GONE
                    empty_note_view.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    empty_note_view.visibility = View.GONE
                }
            }
        })


        fab_add_note.setOnClickListener {
            val intent = Intent(this@MainActivity, NewNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val content = it.getStringExtra(NewNoteActivity.EXTRA_CONTENT)
                val font_style = it.getIntExtra(NewNoteActivity.EXTRA_FONT_STYLE, 0)

                val note = Note(content, font_style)

                mNotesViewModel?.insert(note)
                Toasty.success(this, "Note Saved", Toast.LENGTH_LONG).show()
            }


        } else {
            Toasty.error(this@MainActivity, "Note not saved", Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemDelete(note: Note) {
        mNotesViewModel?.delete(note)
        notesListAdapter?.notifyDataSetChanged()
        Toasty.success(this@MainActivity, "Note Deleted", Toast.LENGTH_LONG).show()

    }


    companion object {
        const val ADD_NOTE_REQUEST = 199
    }
}
