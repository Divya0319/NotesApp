package com.practicesession.notesapp.helpers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practicesession.notesapp.dao.NotesDao
import com.practicesession.notesapp.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase? {
            if (INSTANCE == null) {

                synchronized(this) {
                    INSTANCE =
                            Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java, "note_database")
                                .fallbackToDestructiveMigration()
                                .build()
                }
            }
            return INSTANCE

        }
    }
}
