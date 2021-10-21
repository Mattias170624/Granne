package com.example.granne

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class SettingsDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_settings_dialog, container, false)

        val usernameEditText = rootView.findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = rootView.findViewById<EditText>(R.id.passwordEditText)

        val usernameButton = rootView.findViewById<Button>(R.id.usernameButton)
        val passwordButton = rootView.findViewById<Button>(R.id.passwordButton)


        usernameButton.setOnClickListener {
            val usernameText = usernameEditText.text.toString()

            when (usernameText.isEmpty()) {
                true -> showToast("Please enter a Username")

                false -> {
                    if (!checkForExistingUserDetails(usernameText)) {
                        Log.d("!", "No username found")
                    } else {
                        showToast("Username already exists!")
                    }
                }
            }
        }

        passwordButton.setOnClickListener {
            val passwordText = passwordEditText.text.toString()
            showToast("Password button pressed")
            Log.d("!", "Password = $passwordText")

            // Ändra databas password till det som står i passwordText
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