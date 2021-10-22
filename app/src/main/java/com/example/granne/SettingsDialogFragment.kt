package com.example.granne

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import android.widget.AdapterView
import kotlin.math.log


class SettingsDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_settings_dialog, container, false)

        val usernameEditText = rootView.findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = rootView.findViewById<EditText>(R.id.passwordEditText)
        val locations = resources.getStringArray(R.array.Locations)
        val spinner = rootView.findViewById<Spinner>(R.id.spinnerLocation)

        val usernameButton = rootView.findViewById<Button>(R.id.usernameButton)
        val passwordButton = rootView.findViewById<Button>(R.id.passwordButton)

        usernameButton.setOnClickListener {
            val usernameText = usernameEditText.text.toString()

            when (usernameText.isEmpty()) {
                true -> showToast("Please enter a Username!")

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

            when (passwordText.isEmpty()) {
                true -> showToast("Please enter a Password!")

                false -> {
                    if (!checkForExistingUserDetails(passwordText)) {
                        Log.d("!", "No password found")
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
        // Test edit
        return false
    }

    fun showToast(toastMessage: String) {
        val toast = Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}