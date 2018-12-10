package com.practicesession.notesapp.helpers

import com.practicesession.notesapp.model.Note

interface ItemDeletionListener {
    fun onItemDelete(note: Note)
}