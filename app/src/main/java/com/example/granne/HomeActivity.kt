package com.example.granne

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        var infoButton = findViewById<Button>(R.id.buttonInformation)
        infoButton.setOnClickListener{

            var dialog = CustomDialogFragment()

            dialog.show(supportFragmentManager,"customDialog")
        }






    }



}


