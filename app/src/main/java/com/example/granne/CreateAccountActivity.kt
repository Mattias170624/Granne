package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
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
    lateinit var acceptTermsText: TextView
    lateinit var acceptTermsCheckBox: CheckBox
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
        acceptTermsText = findViewById(R.id.tosText)
        acceptTermsCheckBox = findViewById(R.id.tosCheckBox)
        inputValidation()
        acceptTermsText.setOnClickListener {
            val dialog = TosDialogFragment()
            dialog.show(supportFragmentManager, "tosDialog")
        }

    }
    private fun inputValidation(){
        buttonRegister.setOnClickListener {
            when {
                checkUserInputs() -> {
                    if (password.length >= 6) {
                        if (nickname.length >= 6) {
                            if (acceptTermsCheckBox.isChecked) {
                                createAccount(email, password, nickname)
                            } else showToast("You must accept Terms of Service")
                        } else showToast("Nickname must be longer than 6 characters")
                    } else showToast("Password must be longer than 6 characters")
                }
                !checkUserInputs() -> showToast("Empty inputs!")
            }
        }
    }

    private fun createAccount(email: String, password: String, nickname: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    showToast("Successfully created account")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // Sign in failed
                    Log.w("!", "createUserWithEmail:failure", task.exception)
                    showToast("Email already in use or badly written")
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val startHomeActivityIntent = Intent(this, HomeActivity::class.java)
            startActivity(startHomeActivityIntent)
        }
    }

    private fun checkUserInputs(): Boolean {
        nickname = nicknameEditText.text.toString()
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()
        return !(nickname.isEmpty() || email.isEmpty() || password.isEmpty())
    }

    private fun showToast(toastMessage: String) {
        val toast = Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}
