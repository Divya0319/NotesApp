package com.practicesession.notesapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.practicesession.notesapp.R
import kotlinx.android.synthetic.main.activity_add_edit_note.*

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var spinner: Spinner
    private val fontOptions = arrayOf("Baumans", "Catamaran", "Droid Sans", "Hind Guntur")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        spinner = findViewById(R.id.fontSelector)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, fontOptions)
        spinner.adapter = arrayAdapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        val typeface = ResourcesCompat.getFont(this@AddEditNoteActivity, R.font.baumans)
                        et_content.typeface = typeface
                    }
                    1 -> {
                        val typeface = ResourcesCompat.getFont(this@AddEditNoteActivity, R.font.catamaran)
                        et_content.typeface = typeface
                    }
                    2 -> {
                        val typeface = ResourcesCompat.getFont(this@AddEditNoteActivity, R.font.droid_sans)
                        et_content.typeface = typeface
                    }
                    3 -> {
                        val typeface = ResourcesCompat.getFont(this@AddEditNoteActivity, R.font.hind_guntur)
                        et_content.typeface = typeface
                    }
                }
            }

        }
        val intent = intent
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            et_content.setText(intent.getStringExtra(EXTRA_CONTENT))
            spinner.setSelection(intent.getIntExtra(EXTRA_FONT_STYLE, 0))
        } else {
            title = "Add Note"
        }

        bt_save.setOnClickListener {
            saveNote()
        }

    }

    private fun saveNote() {
        val content = et_content.text.toString()
        val spinnerPosition = spinner.selectedItemPosition
        if (TextUtils.isEmpty(et_content.text)) {
            et_content.error = "Title cannot be empty"
            return
        }
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_CONTENT, content)
        replyIntent.putExtra(EXTRA_FONT_STYLE, spinnerPosition)

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            replyIntent.putExtra(EXTRA_ID, id)
        }

        setResult(Activity.RESULT_OK, replyIntent)
        finish()

    }

    companion object {
        const val EXTRA_ID = "com.practicesession.notesapp.activities.EXTRA_ID"
        const val EXTRA_CONTENT = "com.practicesession.notesapp.activities.EXTRA_CONTENT"
        const val EXTRA_FONT_STYLE = "com.practicesession.notesapp.activities.EXTRA_FONT_STYLE"
    }
}
