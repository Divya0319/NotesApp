package com.practicesession.notesapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicesession.notesapp.R
import com.practicesession.notesapp.adapters.NotesListAdapter
import com.practicesession.notesapp.model.Note
import com.practicesession.notesapp.model.NotesViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var notesListAdapter: NotesListAdapter? = null

    private lateinit var mNotesViewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.rv_notes)
        notesListAdapter = NotesListAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = notesListAdapter

        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_rec_view))
        recyclerView.addItemDecoration(itemDecoration)

        mNotesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)

        mNotesViewModel.getAllNotes().observe(this, Observer { notes ->
            notes.let {
                notesListAdapter?.setNotes(it)

                val swipeToDeleteCallback = object :
                    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        mNotesViewModel.delete(notesListAdapter!!.getNoteAt(viewHolder.adapterPosition))
                        Toasty.success(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
                    }
                }

                ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(recyclerView)

                notesListAdapter?.setOnItemClickListener(object : NotesListAdapter.OnItemClickListener {
                    override fun onItemClick(note: Note) {
                        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
                        intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
                        intent.putExtra(AddEditNoteActivity.EXTRA_CONTENT, note.content)
                        intent.putExtra(AddEditNoteActivity.EXTRA_FONT_STYLE, note.fontStyle)
                        startActivityForResult(intent, EDIT_NOTE_REQUEST)
                    }
                })

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
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val content = it.getStringExtra(AddEditNoteActivity.EXTRA_CONTENT)
                val font_style = it.getIntExtra(AddEditNoteActivity.EXTRA_FONT_STYLE, 0)

                val note = Note(content, font_style)

                mNotesViewModel.insert(note)
                Toasty.success(this, "Note Saved", Toast.LENGTH_SHORT).show()
            }


        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)
            if (id == -1) {
                Toasty.error(this@MainActivity, "Note can't be updated", Toast.LENGTH_SHORT).show()
                return
            } else {
                data?.let {
                    val content = it.getStringExtra(AddEditNoteActivity.EXTRA_CONTENT)
                    val font_style = it.getIntExtra(AddEditNoteActivity.EXTRA_FONT_STYLE, 0)
                    val note = Note(content, font_style)
                    note.id = id
                    mNotesViewModel.update(note)

                    Toasty.success(this@MainActivity, "Note Updated", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            Toasty.error(this@MainActivity, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val ADD_NOTE_REQUEST = 199
        const val EDIT_NOTE_REQUEST = 299
    }
}
