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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = Firebase.auth

        lateinit var btn_submitPassword: Button
        lateinit var et_enterEmail: EditText

        btn_submitPassword = findViewById(R.id.btn_submitPassword)
        et_enterEmail = findViewById(R.id.et_enterEmail)

        btn_submitPassword.setOnClickListener {
            val email: String = et_enterEmail.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                Log.d(TAG, "Enter Email")
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Please enter your email adress.",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                Firebase.auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Log.d(TAG, "Email sent")
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "Email successfully sent to reset your password.",
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                        }
                        else{
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }
}