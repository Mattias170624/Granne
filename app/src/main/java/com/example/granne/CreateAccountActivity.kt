package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CreateAccountActivity : AppCompatActivity() {

    lateinit var createUsernameEditText: EditText
    lateinit var createPasswordEditText: EditText
    lateinit var createAboutYourselfEditText: EditText
    lateinit var createPhoneNumberEditText: EditText
    lateinit var createConfirmPhoneNumberEditText: EditText
    private lateinit var createNewAccountButton: Button

    lateinit var usernameText: String
    lateinit var passwordText: String
    lateinit var aboutYourselfText: String
    lateinit var phoneNumberText: String
    lateinit var phoneCodeText: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        createUsernameEditText = findViewById(R.id.createUsernameEditText)
        createPasswordEditText = findViewById(R.id.createPasswordEditText)
        createAboutYourselfEditText = findViewById(R.id.createAboutYourselfEditText)
        createPhoneNumberEditText = findViewById(R.id.createPhoneNumberEditText)
        createConfirmPhoneNumberEditText = findViewById(R.id.createConfirmPhoneNumberEditText)
        createNewAccountButton = findViewById(R.id.createNewAccountButton)


        createNewAccountButton.setOnClickListener {
            when {
                checkUserInputs() -> {
                    Log.d("!", "User inputs are good")
                    createUserInFirebase()

                    val createNewAccountActivity = Intent(this, HomeActivity::class.java)
                    startActivity(createNewAccountActivity)
                }
                !checkUserInputs() -> {
                    Log.d("!", "Some texts are empty")
                    showToast("Some fields are missing!")
                }
            }
        }


    }

    private fun createUserInFirebase() {

        // Här skapas en firebase User med:
        //
        // usernameText, passwordText, aboutYourselfText, etc...
        //
        // Sen efter den här funktionen är klar, så körs ju Intent där man blir skickad till mainscreenen.

    }

    private fun checkUserInputs(): Boolean {
        usernameText = createUsernameEditText.text.toString()
        passwordText = createPasswordEditText.text.toString()
        aboutYourselfText = createAboutYourselfEditText.text.toString()
        phoneNumberText =
            createPhoneNumberEditText.toString()         // Phone number och Phone code
        phoneCodeText = createConfirmPhoneNumberEditText.toString()    // används inte just nu

        return !(usernameText.isEmpty() || passwordText.isEmpty() || aboutYourselfText.isEmpty())
    }

    fun showToast(toastMessage: String) {
        val toast = Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}