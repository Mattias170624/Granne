package com.example.granne

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class HomeActivity : AppCompatActivity() {

    lateinit var buttonOptions: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        buttonOptions = findViewById(R.id.buttonOptions)

        buttonOptions.setOnClickListener {
            var dialog = SettingsDialogFragment()
            dialog.show(supportFragmentManager, "optionsdialog")
        }

        var infoButton = findViewById<Button>(R.id.buttonInformation)
        infoButton.setOnClickListener{

            var dialog = CustomDialogFragment()

            dialog.show(supportFragmentManager,"customDialog")
        }

    }
}
