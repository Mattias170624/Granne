package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    lateinit var buttonRegister: Button
    lateinit var nicknameEditText: EditText
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText

    lateinit var nickname: String
    lateinit var email: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        auth = Firebase.auth

        buttonRegister = findViewById(R.id.buttonRegister)
        nicknameEditText = findViewById(R.id.nicknameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        buttonRegister.setOnClickListener {
            when {
                checkUserInputs() -> {
                    if (password.length >= 6) {
                        if (nickname.length >= 6) {
                            createAccount(email, password, nickname)
                        } else showToast("Nickname must be longer than 6 characters")
                    } else showToast("Password must be longer than 6 characters")
                }

                !checkUserInputs() -> showToast("Empty inputs!")
            }
        }

    }

    private fun createAccount(email: String, password: String, nickname: String) {
        //1
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                        //2
                    showToast("Successfully created account")
                    val user = auth.currentUser
                    val uid: String = user!!.uid

                    val currentUser = hashMapOf(
                        "nickname" to nickname,
                        "email" to email,
                        "uid" to user.uid,
                        "location" to "",
                        "aboutme" to "",  //  These will be filled later in the app
                    )

                    db.collection("userData")
                        .document(uid).set(currentUser)
                        .addOnSuccessListener {
                            //3
                            Log.d("!", "User added to Database with same UID as Firestore Auth ")
                        }
                        .addOnFailureListener { e ->
                            Log.w("!", "Error adding document", e)
                        }
            //4
                    updateUI(user)

                } else {
                    // Sign in failed
                    Log.w("!", "createUserWithEmail:failure", task.exception)
                    showToast("Email already in use or badly written")
                    updateUI(null)
                }
            }
        //5
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Log.d("!", "Account created / logged in")
            Log.d("!", "----------------------------")
            Log.d("!", "Nickname: $nickname")
            Log.d("!", "Email: $email")
            Log.d("!", "Uid: ${user.uid}")

            val startHomeActivityIntent = Intent(this, HomeActivity::class.java)
            startActivity(startHomeActivityIntent)

        } else Log.d("!", "User failed to log in")
    }

    private fun checkUserInputs(): Boolean {
        nickname = nicknameEditText.text.toString()
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()

        return !(nickname.isEmpty() || email.isEmpty() || password.isEmpty())
    }


    fun showToast(toastMessage: String) {
        val toast = Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}
