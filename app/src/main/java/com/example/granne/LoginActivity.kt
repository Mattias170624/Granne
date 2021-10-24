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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    val db = Firebase.firestore
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

                        fun startHomeActivity() {


                        }

                    } else {
                        Log.d("!", "No account matched")
                        Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT.)
                    }
                }

                !checkUserInputs() -> {
                    Log.d("!", "Missing some text fields")
                    Toast.makeText(this, "Please enter a username and password", Toast.LENGTH_SHORT)
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
        db.collection("users")
            .whereEqualTo("username", usernameText)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.d("!", "No duplicate found")
                    checkDatabaseForAccount()
                } else {
                    Log.d("!", "Duplicate found!")
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("!", "Error getting documents: ", exception)
                // Compare $usernameText and $passwordText with database...
                true
            }

        fun showToast(toastMessage: String) {
            val toast = Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT)
            toast.show()
        }

        fun startHomeActivity() {
            val startHomeIntent = Intent(this, HomeActivity::class.java)
            startActivity(startHomeIntent)
        }


    return true}

}



