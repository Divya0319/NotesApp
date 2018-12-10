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
import com.practicesession.notesapp.constants.BundleKeys
import kotlinx.android.synthetic.main.activity_new_note.*

class NewNoteActivity : AppCompatActivity() {
    var id = 0
    lateinit var spinner: Spinner
    private val fontOptions = arrayOf("Baumans", "Catamaran", "Droid Sans", "Hind Guntur")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
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
                        val typeface = ResourcesCompat.getFont(this@NewNoteActivity, R.font.baumans)
                        et_content.typeface = typeface
                    }
                    1 -> {
                        val typeface = ResourcesCompat.getFont(this@NewNoteActivity, R.font.catamaran)
                        et_content.typeface = typeface
                    }
                    2 -> {
                        val typeface = ResourcesCompat.getFont(this@NewNoteActivity, R.font.droid_sans)
                        et_content.typeface = typeface
                    }
                    3 -> {
                        val typeface = ResourcesCompat.getFont(this@NewNoteActivity, R.font.hind_guntur)
                        et_content.typeface = typeface
                    }
                }
            }

        }
        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getInt(BundleKeys.bundleId, 0)

            if (id != 0) {
                et_content.setText(bundle.getString(BundleKeys.bundleContent))
                spinner.setSelection(bundle.getInt(BundleKeys.bundleFont, 0))

            }
        }

        bt_save.setOnClickListener {
            saveNote()
        }

    }

    private fun saveNote() {
        val replyIntent = Intent()
        if (id == 0) {
            if (TextUtils.isEmpty(et_content.text)) {
                et_content.error = "Title cannot be empty"
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val content = et_content.text.toString()
                replyIntent.putExtra(EXTRA_CONTENT, content)
                replyIntent.putExtra(EXTRA_FONT_STYLE, spinner.selectedItemPosition)

                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        } else {

        }
    }

    companion object {
        const val EXTRA_ID = "com.practicesession.notesapp.activities.ID"
        const val EXTRA_CONTENT = "com.practicesession.notesapp.activities.EXTRA_CONTENT"
        const val EXTRA_FONT_STYLE = "com.practicesession.notesapp.activities.EXTRA_FONT_STYLE"
    }
}
