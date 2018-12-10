package com.practicesession.notesapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.practicesession.notesapp.repos.NotesRepository
import es.dmoral.toasty.Toasty

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val mNotesRepository: NotesRepository = NotesRepository(application)
    private val mAllNotes: LiveData<List<Note>>

    init {
        mAllNotes = mNotesRepository.getAllNotes()!!
    }

    fun insert(note: Note) {
        mNotesRepository.insert(note)
    }

    fun update(note: Note) {
        mNotesRepository.update(note)
    }

    fun delete(note: Note) {
        mNotesRepository.delete(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return mAllNotes
    }
}