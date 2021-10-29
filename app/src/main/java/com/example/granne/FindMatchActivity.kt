package com.example.granne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox

class FindMatchActivity : AppCompatActivity() {

    lateinit var interestButton: Button
    lateinit var searchMatchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_match)

        interestButton = findViewById(R.id.interestButton)
        searchMatchButton = findViewById(R.id.searchMatchButton)

        interestButton.setOnClickListener {
            val dialog = InterestDialogFragment()
            dialog.show(supportFragmentManager, "interestdialog")
        }

        searchMatchButton.setOnClickListener {
            Log.d("!", "Match button pressed")
            // Sök efter användare med intressena som var Checkade
        }

    }


}