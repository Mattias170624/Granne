package com.example.granne

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.granne.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    private fun showToast(message: String) {
        Toast.makeText(
            this@ForgotPasswordActivity,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmitPassword.setOnClickListener {
            val email: String = binding.etEnterEmail.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                Log.d(TAG, "Enter Email")
                showToast("Please enter your email address.")
            } else {
                Firebase.auth
                    .sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent")
                            showToast("Email successfully sent to reset your password.")
                            finish()
                        } else {
                            task.exception?.message?.let {
                                showToast(it)
                            }
                        }
                    }
            }
        }
    }
}