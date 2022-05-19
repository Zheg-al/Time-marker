package com.example.timemarker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


const val MARK_NAME = "name"

class AddTimeMarkActivity : AppCompatActivity() {
    private lateinit var name: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_mark_layout)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addFlower()
        }
        name = findViewById(R.id.mark_name)
    }

    /* The onClick action for the done button. Closes the activity and returns the new flower name
    and description as part of the intent. If the name or description are missing, the result is set
    to cancelled. */

    private fun addFlower() {
        val resultIntent = Intent()

        val name = name.text.toString()
        resultIntent.putExtra(MARK_NAME, name)
        setResult(Activity.RESULT_OK, resultIntent)

        finish()
    }
}