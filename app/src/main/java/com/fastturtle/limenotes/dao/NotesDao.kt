package com.fastturtle.limenotes.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fastturtle.limenotes.model.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Delete
    fun delete(notes: Note)

    @Update
    fun update(notes: Note)
}