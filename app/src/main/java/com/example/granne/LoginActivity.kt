package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch

class LoginActivity : AppCompatActivity() {

    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var autoLoginSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        autoLoginSwitch = findViewById(R.id.autoLoginSwitch)

    }

    fun startMainScreenActivity(view: View) {

        Log.d("!Login", "Login button pressed. Autologin = ${autoLoginSwitch.isChecked}")
        val startHomeIntent = Intent(this, HomeActivity::class.java)
        startActivity(startHomeIntent)

        // här ska man först kolla om username och password matchar någon användare i databasen
        // sen om det username + password matchar med någon, så ska man skickas till Mainscreen
        //
        // Sen ska informationen om switch (true eller false) uppdateras i databasen
    }
}
