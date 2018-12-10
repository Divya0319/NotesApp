package com.practicesession.notesapp.helpers

import com.practicesession.notesapp.model.Note

interface ItemClickListener {
    fun onItemClick(note: Note)
}