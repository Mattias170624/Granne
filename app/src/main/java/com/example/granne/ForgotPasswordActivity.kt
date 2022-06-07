package com.example.granne

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    lateinit var enterEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = Firebase.auth

        lateinit var buttonSubmitPassword: Button

        buttonSubmitPassword = findViewById(R.id.btn_submitPassword)
        enterEmail = findViewById(R.id.et_enterEmail)

        buttonSubmitPassword.setOnClickListener {
            submitPassword()
        }
    }

        private fun submitPassword() {
            val email: String = enterEmail.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                Log.d(TAG, "Enter Email")
                showShortToast("Please enter your email adress.")
            }else{
                Firebase.auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Log.d(TAG, "Email sent")
                            showLongToast("Email successfully sent to reset your password.")
                                finish()
                        }
                        else{
                            showLongToast(task.exception!!.message.toString())
                }
            }
        }
    }

    private fun showShortToast(toastMessage: String) {
            val toast = Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT)
            toast.show()
    }

    private fun showLongToast(toastMessage: String) {
        val toast = Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_LONG)
        toast.show()
        }
    }
