package com.practicesession.notesapp.repos

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.practicesession.notesapp.dao.NotesDao
import com.practicesession.notesapp.helpers.NotesDatabase
import com.practicesession.notesapp.model.Note

class NotesRepository(application: Application) {
    private var notesDao: NotesDao? = null
    private var mAllNotes: LiveData<List<Note>>? = null

    init {
        val database = NotesDatabase.getInstance(application)
        if (database != null) {
            notesDao = database.notesDao()
            mAllNotes = notesDao?.getAllNotes()
        }
    }

    fun getAllNotes(): LiveData<List<Note>>? {
        return mAllNotes
    }

    fun insert(note: Note) {
        notesDao?.let { InsertNoteAsyncTask(it).execute(note) }
    }

    fun update(note: Note) {
        notesDao?.let { UpdateNoteAsyncTask(it).execute(note) }
    }

    fun delete(note: Note) {
        notesDao?.let { DeleteNoteAsyncTask(it).execute(note) }
    }

    companion object {
        private class InsertNoteAsyncTask(noteDao: NotesDao) : AsyncTask<Note, Unit, Unit>() {
            private var noteDao: NotesDao? = null

            init {
                this.noteDao = noteDao
            }

            override fun doInBackground(vararg note: Note) {

                noteDao?.insert(note[0])
            }
        }

        private class UpdateNoteAsyncTask(noteDao: NotesDao) : AsyncTask<Note, Unit, Unit>() {
            private var noteDao: NotesDao? = null

            init {
                this.noteDao = noteDao
            }

            override fun doInBackground(vararg note: Note) {

                noteDao?.update(note[0])
            }
        }

        private class DeleteNoteAsyncTask(noteDao: NotesDao) : AsyncTask<Note, Unit, Unit>() {
            private var noteDao: NotesDao? = null

            init {
                this.noteDao = noteDao
            }

            override fun doInBackground(vararg note: Note) {

                noteDao?.delete(note[0])
            }
        }
    }
}