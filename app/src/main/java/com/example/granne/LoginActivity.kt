package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton2: Button
    lateinit var autoLoginSwitch: Switch

    lateinit var usernameText: String
    lateinit var passwordText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton2 = findViewById(R.id.loginButton2)
        autoLoginSwitch = findViewById(R.id.autoLoginSwitch)


        loginButton2.setOnClickListener {
            Log.d("!Login", "Login button2 pressed")
            usernameText = usernameEditText.text.toString()
            passwordText = passwordEditText.text.toString()

            when {
                checkUserInputs() -> {
                    if (checkDatabaseForAccount()) {
                        //autoLoginChecker()
                        // Om checkDataBaseForAccount returnerar ett true, så ska man tas till homeScreenActivity
                        // med användarens data.

                        startHomeActivity()

                    } else {
                        Log.d("!", "No account matched")
                        showToast("Invalid username or password")
                    }
                }

                !checkUserInputs() -> {
                    Log.d("!", "Missing some text fields")
                    showToast("Please enter a username and password")
                }
            }
        }
    }

    private fun autoLoginChecker(): Boolean { // SharedPreferences om användaren vill bli auto inloggad
        if (!autoLoginSwitch.isChecked) {
            Log.d("!", "Auto login False")
            return false
        } else {
            Log.d("!", "Auto login: True")
            return true
        }
    }

    private fun checkUserInputs(): Boolean {
        return !(usernameText.isEmpty() || passwordText.isEmpty())
    }

    private fun checkDatabaseForAccount(): Boolean {
        // Compare $usernameText and $passwordText with database...
        return true
    }

    fun showToast(toastMessage: String) {
        val toast = Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun startHomeActivity() {
        val startHomeIntent = Intent(this, HomeActivity::class.java)
        startActivity(startHomeIntent)
    }
}
