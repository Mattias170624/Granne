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
        val nicknameButton = rootView.findViewById<Button>(R.id.nicknameButton)
        val spinner = rootView.findViewById<Spinner>(R.id.spinnerLocation)
        val buttonSignOut = rootView.findViewById<Button>(R.id.buttonSignOut)
        val locationButton = rootView.findViewById<Button>(R.id.locationButton)
        val deleteAccountButton = rootView.findViewById<Button>(R.id.deleteAccountButton)

        auth = Firebase.auth

        val currentUser = auth.currentUser

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
                            .addOnSuccessListener {
                                showToast("Nickname changed!")
                                nicknameEditText.setText("")
                            }

                            .addOnFailureListener { e -> Log.d("!", "Error:", e) }
                    }
                }
            }
        }

        val locations = listOf(
            "Svealand",
            "Götaland",
            "Norrland"
        )

        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, locations)
        spinner.adapter = arrayAdapter

        locationButton.setOnClickListener {
            val newLocation = spinner.selectedItem.toString()

            db.collection("userData").document(currentUser!!.uid)
                .update("location", newLocation)
                .addOnSuccessListener {
                    showToast("Updated location")
                }
                .addOnFailureListener { e ->
                    Log.w("!", "Error adding document", e)
                }
        }

        buttonSignOut.setOnClickListener {
            showToast("Logged out")
            auth.signOut()
            val startLoginScreen = Intent(activity, MainActivity::class.java)
            startActivity(startLoginScreen)
        }

        deleteAccountButton.setOnClickListener {
            db.collection("userData").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .delete()
                .addOnSuccessListener {
                    FirebaseAuth.getInstance().currentUser!!.delete()
                        .addOnCompleteListener {
                            Log.d("!", "User deleted in cloud database and auth")
                            showToast("Account deleted!")
                            val returnToLoginScreen = Intent(activity, MainActivity::class.java)
                            startActivity(returnToLoginScreen)
                        }
                }
                .addOnFailureListener { error ->
                    Log.d("!", "Error! $error")
                }
        }

        return rootView
    }

    private fun showToast(toastMessage: String) {
        val toast = Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}