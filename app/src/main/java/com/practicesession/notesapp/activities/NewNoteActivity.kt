package com.practicesession.notesapp.activities

import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.practicesession.notesapp.constants.BundleKeys
import com.practicesession.notesapp.R
import com.practicesession.notesapp.dbhelper.NoteDBManagar
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_new_note.*

class NewNoteActivity : AppCompatActivity() {
    var id = 0
    private val fontOptions = arrayOf("Baumans", "Catamaran", "Droid Sans", "Hind Guntur")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        val spinner = findViewById<Spinner>(R.id.fontSelector)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, fontOptions)
        if (spinner != null) {
            spinner.adapter = arrayAdapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        val typeface = ResourcesCompat.getFont(this@NewNoteActivity, R.font.baumans)
                        et_title.typeface = typeface
                        et_content.typeface = typeface
                    }
                    1 -> {
                        val typeface = ResourcesCompat.getFont(this@NewNoteActivity, R.font.catamaran)
                        et_title.typeface = typeface
                        et_content.typeface = typeface
                    }
                    2 -> {
                        val typeface = ResourcesCompat.getFont(this@NewNoteActivity, R.font.droid_sans)
                        et_title.typeface = typeface
                        et_content.typeface = typeface
                    }
                    3 -> {
                        val typeface = ResourcesCompat.getFont(this@NewNoteActivity, R.font.hind_guntur)
                        et_title.typeface = typeface
                        et_content.typeface = typeface
                    }
                }
            }

        }
        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getInt(BundleKeys.bundleId, 0)
            try {
                if (id != 0) {
                    et_title.setText(bundle.getString(BundleKeys.bundleTitle))
                    et_content.setText(bundle.getString(BundleKeys.bundleContent))
                    spinner.setSelection(bundle.getInt(BundleKeys.bundleFont,0))

                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        bt_add.setOnClickListener {
            val dbManager = NoteDBManagar(this)
            val values = ContentValues()
            if (TextUtils.isEmpty(et_title.text)) {
                et_title.error = "Title cannot be empty"
            } else {
                values.put("Title", et_title.text.toString())
                values.put("Content", et_content.text.toString())
                values.put("FontStyle", spinner.selectedItemPosition)

                if (id == 0) {
                    val mId = dbManager.insert(values)
                    if (mId > 0) {
                        Toasty.success(this, "Note added successfully", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toasty.error(this, "Failed to add note!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                } else {
                    val selectionArgs = arrayOf(id.toString())
                    val mId = dbManager.update(values, "Id=?", selectionArgs)
                    if (mId > 0) {
                        Toasty.success(this, "Note updated successfully", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toasty.error(this, "Failed to update note!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
        }
    }
}
