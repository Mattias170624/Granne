package com.example.granne

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import android.widget.AdapterView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SettingsDialogFragment : DialogFragment() {

    private lateinit var auth: FirebaseAuth

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_settings_dialog, container, false)

        val nicknameEditText = rootView.findViewById<EditText>(R.id.nicknameEditText)
        val passwordEditText = rootView.findViewById<EditText>(R.id.passwordEditText)
        val locations = resources.getStringArray(R.array.Locations)
        val spinner = rootView.findViewById<Spinner>(R.id.spinnerLocation)

        val nicknameButton = rootView.findViewById<Button>(R.id.nicknameButton)
        val passwordButton = rootView.findViewById<Button>(R.id.passwordButton)
        val deleteAccountButton = rootView.findViewById<Button>(R.id.deleteAccountButton)

        auth = Firebase.auth

        val currentUser = auth.currentUser

        deleteAccountButton.setOnClickListener {

            db.collection("userData").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .delete()
                .addOnSuccessListener {
                    FirebaseAuth.getInstance().currentUser!!.delete()
                        .addOnCompleteListener {
                            Log.d("!", "User deleted in cloud database and auth")
                            val returnToLoginScreen = Intent(activity, MainActivity::class.java)
                            startActivity(returnToLoginScreen)
                        }
                }
                .addOnFailureListener { error ->
                    Log.d("!", "Error! $error")
                }
        }

        nicknameButton.setOnClickListener {
            val nickname = nicknameEditText.text.toString()
            when (nickname.isEmpty()) {
                true -> showToast("Please enter a new username!")

                false -> {
                    if (nickname.length < 6) {
                        showToast("Username cant be less than 6 characters")
                    } else {
                        db.collection("userData").document(currentUser!!.uid)
                            .update("nickname", nickname)
                            .addOnSuccessListener { Log.d("!", "Sucess!!") }
                            .addOnFailureListener { e -> Log.d("!", "Error:", e) }
                    }
                }
            }
        }

        passwordButton.setOnClickListener {
            val passwordText = passwordEditText.text.toString()

            when (passwordText.isEmpty()) {
                true -> showToast("Please enter a Password!")

                false -> {
                    if (!checkForExistingUserDetails(passwordText)) {
                        Log.d("!", "No password found")
                        // Change the password in the database


                    } else {
                        showToast("Password already exists!")
                    }
                }
            }
        }

        if (spinner != null) {
            val adapter = activity?.let {
                ArrayAdapter(it, android.R.layout.simple_spinner_item, locations)
            }
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                when (position) { // Position = spinner options converted into Integer
                    0 -> {
                        // Change user location in database to Svealand
                        Log.d("!", "Svealand")
                    }
                    1 -> {
                        // Change user location in database to Götaland
                        Log.d("!", "Götaland")
                    }
                    2 -> {
                        // Change user location in database to Norrland
                        Log.d("!", "Norrland")
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        return rootView
    }

    private fun checkForExistingUserDetails(text: String): Boolean {
        // jämför med databasen om det finns användare där text == username eller password
        return false
    }

    fun showToast(toastMessage: String) {
        val toast = Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}