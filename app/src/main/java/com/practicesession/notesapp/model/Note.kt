package com.practicesession.notesapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(@ColumnInfo(name = "Content") var content: String, @ColumnInfo(name = "FontStyle") var fontStyle: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}
