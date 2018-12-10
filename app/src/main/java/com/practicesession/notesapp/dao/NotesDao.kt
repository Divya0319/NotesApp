package com.practicesession.notesapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.practicesession.notesapp.model.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table ORDER BY Content ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Delete
    fun delete(notes: Note)

    @Update
    fun update(notes: Note)
}