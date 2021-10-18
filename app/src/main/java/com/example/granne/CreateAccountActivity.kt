package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateAccountActivity : AppCompatActivity() {

    lateinit var createNicknameEditText: EditText
    lateinit var createUsernameEditText: EditText
    lateinit var createPasswordEditText: EditText
    lateinit var createAboutMeEditText: EditText
    lateinit var createPhoneNumberEditText: EditText
    lateinit var createConfirmPhoneNumberEditText: EditText
    private lateinit var createNewAccountButton: Button

    lateinit var nicknameText: String
    lateinit var usernameText: String
    lateinit var passwordText: String
    lateinit var aboutMeText: String
    lateinit var phoneNumberText: String
    lateinit var phoneCodeText: String

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        createNicknameEditText = findViewById(R.id.createNicknameEditText)
        createUsernameEditText = findViewById(R.id.createUsernameEditText)
        createPasswordEditText = findViewById(R.id.createPasswordEditText)
        createAboutMeEditText = findViewById(R.id.createAboutYourselfEditText)
        createPhoneNumberEditText = findViewById(R.id.createPhoneNumberEditText)
        createConfirmPhoneNumberEditText = findViewById(R.id.createConfirmPhoneNumberEditText)
        createNewAccountButton = findViewById(R.id.createNewAccountButton)


        createNewAccountButton.setOnClickListener {
            when {
                checkUserInputs() -> {
                    Log.d("!", "User inputs are good")
                    checkForDuplicateAccount()
                }
                !checkUserInputs() -> {
                    Log.d("!", "Some texts are empty")
                    showToast("Some fields are missing!")
                }
            }
        }
    }

    private fun checkForDuplicateAccount() {
        db.collection("users")
            .whereEqualTo("nickname", nicknameText)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.d("!", "No duplicate found")
                    createUserInFirebase()
                } else {
                    Log.d("!", "Duplicate found")
                    showToast("Nickname already exists")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("!", "Error getting documents: ", exception)
            }
    }

    private fun createUserInFirebase() {  // Adds the user inputs in database
        val user = hashMapOf(
            "nickname" to nicknameText,
            "username" to usernameText,
            "password" to passwordText,
            "aboutMe" to aboutMeText
            // To be added: phone number and confirmation code
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("!", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("!", "Error adding document", e)
            }

        val createNewAccountActivity = Intent(this, HomeActivity::class.java)
        startActivity(createNewAccountActivity)
    }

    private fun checkUserInputs(): Boolean {
        nicknameText = createNicknameEditText.text.toString()
        usernameText = createUsernameEditText.text.toString()
        passwordText = createPasswordEditText.text.toString()
        aboutMeText = createAboutMeEditText.text.toString()
        phoneNumberText = createPhoneNumberEditText.toString()       // Phone number och Phone code
        phoneCodeText = createConfirmPhoneNumberEditText.toString()  // används inte just nu

        return !(nicknameText.isEmpty() || usernameText.isEmpty() || passwordText.isEmpty() || aboutMeText.isEmpty())
    }

    fun showToast(toastMessage: String) {
        val toast = Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}