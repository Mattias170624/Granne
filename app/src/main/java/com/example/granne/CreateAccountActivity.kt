package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)


    }

    fun startMainScreenActivity(view: View) {
        Log.d("!CreateAccount", "Pressed Create account button")
        val createNewAccountActivity = Intent(this, HomeActivity::class.java)
        startActivity(createNewAccountActivity)
    }

}