package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.Toast


class HomeActivity : AppCompatActivity() {

    lateinit var buttonOptions: Button
    lateinit var buttonFindMatch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    
        buttonOptions = findViewById(R.id.buttonOptions)
        buttonFindMatch = findViewById(R.id.buttonFindMatch)

        buttonOptions.setOnClickListener {
            var dialog = SettingsDialogFragment()
            dialog.show(supportFragmentManager, "optionsdialog")
        }

        buttonFindMatch.setOnClickListener {
            val findMatchIntent = Intent(this, FindMatchActivity::class.java)
            startActivity(findMatchIntent)
        }

        var infoButton = findViewById<Button>(R.id.buttonInformation)
        infoButton.setOnClickListener{

            var dialog = CustomDialogFragment()

            dialog.show(supportFragmentManager,"customDialog")
        }

    }
    
        fun statsDialogButton(view: View) {
        var statsDialogFragment = StatsDialogFragment()
        statsDialogFragment.show(supportFragmentManager, "statsDialog")
        }

}
