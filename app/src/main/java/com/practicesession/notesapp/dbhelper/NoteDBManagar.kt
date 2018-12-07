package com.practicesession.notesapp.dbhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import es.dmoral.toasty.Toasty

class NoteDBManagar {

    val CREATE_TABLE_COMMAND =
        "CREATE TABLE IF NOT EXISTS $dbTable ($colId INTEGER PRIMARY KEY, $colTitle TEXT, $colContent TEXT, $colFontStyle INTEGER);"
    private var db: SQLiteDatabase? = null

    constructor(context: Context) {
        val dbHelper = DatabaseHelper(context)
        db = dbHelper.writableDatabase
    }

    fun insert(values: ContentValues): Long {
        return db!!.insert(dbTable, "", values)

    }

    fun queryAll(): Cursor {
        return db!!.rawQuery("SELECT * FROM $dbTable", null)
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        return db!!.delete(dbTable, selection, selectionArgs)
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        return db!!.update(dbTable, values, selection, selectionArgs)
    }


    inner class DatabaseHelper : SQLiteOpenHelper {
        var context: Context

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(CREATE_TABLE_COMMAND)
            Toasty.success(this.context, "Database is created", Toast.LENGTH_LONG).show()

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS $dbTable")
            onCreate(db)
        }

        constructor(context: Context) : super(context, dbName, null, dbVersion) {
            this.context = context
        }
    }

    companion object {
        const val dbName = "SKUNotes"
        const val dbTable = "Notes"
        const val colId = "Id"
        const val colTitle = "Title"
        const val colContent = "Content"
        const val colFontStyle = "FontStyle"
        const val dbVersion = 1
    }
}