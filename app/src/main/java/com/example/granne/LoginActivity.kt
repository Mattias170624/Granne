package com.example.granne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

class LoginActivity : AppCompatActivity() {

    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

    }

    fun startMainScreenActivity(view: View) {
        Log.d("!Login", "Login button pressed")

        // här ska man först kolla om username och password matchar någon användare i databasen
        // sen om det username + password matchar med någon, så ska man skickas till Mainscreen
    }
}