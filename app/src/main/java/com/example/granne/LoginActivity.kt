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

    lateinit var buttonSignIn: Button
    lateinit var buttonRegister: Button
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText

    lateinit var email: String
    lateinit var password: String

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        buttonSignIn = findViewById(R.id.buttonSignIn)
        buttonRegister = findViewById(R.id.buttonRegister)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)


        buttonSignIn.setOnClickListener {
            when {
                checkUserInputs() -> {
                    if (password.length >= 6) {
                        signIn(email, password)
                    } else showToast("Password must be at least 6 characters")
                }

                !checkUserInputs() -> showToast("Empty inputs")
            }
        }

        buttonRegister.setOnClickListener {
            when {
                checkUserInputs() -> {
                    if (password.length >= 6) {
                        createAccount(email, password)
                    } else showToast("Password must be at least 6 characters")
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

    fun checkUserInputs(): Boolean {
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()

        return !(email.isEmpty() || password.isEmpty())
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    showToast("Successfully created account")
                    val user = auth.currentUser
                    val uid: String = user!!.uid

                    val currentUser = hashMapOf(
                        "email" to email,
                        "uid" to user.uid,
                    )

                    db.collection("userData")
                        .document(uid).set(currentUser)
                        .addOnSuccessListener {
                            Log.d("!", "User added to Database with same UID as Firestore Auth ")
                        }
                        .addOnFailureListener { e ->
                            Log.w("!", "Error adding document", e)
                        }

                    updateUI(user)

                } else {
                    // Sign in failed
                    Log.w("!", "createUserWithEmail:failure", task.exception)
                    showToast("Email already in use or badly written")
                    updateUI(null)
                }
            }
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
            Log.d("!", "Account created / logged in")
            Log.d("!", "----------------------------")
            Log.d("!", "Email: ${email}")
            Log.d("!", "Uid: ${user.uid}")

            homeScreenIntent()

        } else Log.d("!", "User failed to log in")
    }

    private fun reload() {
        // Reload == keep user logged in
        val user = auth.currentUser
        Log.d("!", "${user?.email} is already logged in")

        homeScreenIntent()
    }

    fun homeScreenIntent() {
        val startHomeActivityIntent = Intent(this, HomeActivity::class.java)
        startActivity(startHomeActivityIntent)
    }

    fun showToast(toastMessage: String) {
        val toast = Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}
