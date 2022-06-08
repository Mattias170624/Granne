package com.example.granne

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.granne.databinding.ActivityForgotPasswordBinding
import com.example.granne.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmitPassword.setOnClickListener {
            val email: String = binding.etEnterEmail.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                Log.d(TAG, "Enter Email")
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Please enter your email adress.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Firebase.auth
                    .sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent")
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "Email successfully sent to reset your password.",
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                        } else {
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