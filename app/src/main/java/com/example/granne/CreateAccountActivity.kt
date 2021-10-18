package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateAccountActivity : AppCompatActivity() {

    val db = Firebase.firestore

    lateinit var  auth : FirebaseAuth
    lateinit var textUsername : EditText
    lateinit var  textPassword : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        auth = Firebase.auth

        textUsername = findViewById(R.id.createUsernameEditText)
        textPassword = findViewById(R.id.createPasswordEditText)


    }

    fun startMainScreenActivity(view: View) {
        Log.d("!CreateAccount", "Pressed Create account button")
        val createNewAccountActivity = Intent(this, HomeActivity::class.java)
        startActivity(createNewAccountActivity)
    }

    fun createUser (){

        val userName = textUsername.text.toString()
        val password = textPassword.text.toString()

        //om man inte skriver in ett username så säger den till. samma sak gäller password
        if(userName.isEmpty() || password.isEmpty())

            return


    }

}