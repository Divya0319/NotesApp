package com.fastturtle.limenotes.helpers

import com.fastturtle.limenotes.model.Note

interface ItemClickListener {
    fun onItemClick(note: Note)
}