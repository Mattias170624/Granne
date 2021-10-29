package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    lateinit var buttonSignIn: Button
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText

    lateinit var email: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        buttonSignIn = findViewById(R.id.buttonSignIn)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)


        buttonSignIn.setOnClickListener {
            when {
                checkUserInputs() -> {
                    if (password.length < 6) {
                        showToast("Password must be at least 6 characters")
                    } else signIn(email, password)
                }

                !checkUserInputs() -> showToast("Empty inputs")
            }
        }

    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null) { // Check if user is signed in
            reload()
        } else {
            Log.d("!", "No user logged in")
        }
    }

    private fun checkUserInputs(): Boolean {
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()

        return !(email.isEmpty() || password.isEmpty())
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    // Sign in success. Start HomeActivity
                    Log.d("!", "signInWithEmail:success")
                    showToast("Logged in")
                    val user = auth.currentUser
                    updateUI(user)

                } else {
                    // If sign in fails. Display a toast to the user
                    Log.w("!", "signInWithEmail:failure", task.exception)
                    showToast("Email doesn't exist or is badly written")
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Log.d("!", "Logged in")
            homeScreenIntent()

        } else Log.d("!", "User failed to log in")
    }

    private fun reload() {
        // Reload == keep user logged in
        val user = auth.currentUser
        Log.d("!", "Auto logged in with email: ${user?.email}")

        homeScreenIntent()
    }

    private fun homeScreenIntent() {
        val startHomeActivityIntent = Intent(this, HomeActivity::class.java)
        startActivity(startHomeActivityIntent)
    }

    private fun showToast(toastMessage: String) {
        val toast = Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}
