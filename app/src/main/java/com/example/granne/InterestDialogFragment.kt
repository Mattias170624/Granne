package com.example.granne

import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.HashMap

class InterestDialogFragment : DialogFragment() {

    private lateinit var aboutMeEditText: EditText

    private lateinit var checkBoxWildlife: CheckBox
    private lateinit var checkBoxTravel: CheckBox
    private lateinit var checkBoxFood: CheckBox
    private lateinit var checkBoxSocializing: CheckBox
    private lateinit var checkBoxBooks: CheckBox
    private lateinit var checkBoxGames: CheckBox
    private lateinit var checkBoxNetflix: CheckBox
    lateinit var saveChangesButton: Button

    lateinit var auth: FirebaseAuth

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_interest_dialog, container, false)

        auth = FirebaseAuth.getInstance()

        aboutMeEditText = rootView.findViewById(R.id.aboutmeEditText)
        checkBoxWildlife = rootView.findViewById(R.id.checkBox1)
        checkBoxTravel = rootView.findViewById(R.id.checkBox2)
        checkBoxFood = rootView.findViewById(R.id.checkBox3)
        checkBoxSocializing = rootView.findViewById(R.id.checkBox4)
        checkBoxBooks = rootView.findViewById(R.id.checkBox5)
        checkBoxGames = rootView.findViewById(R.id.checkBox6)
        checkBoxNetflix = rootView.findViewById(R.id.checkBox7)
        saveChangesButton = rootView.findViewById(R.id.saveChangesButton)

        saveChangesButton.setOnClickListener {
            saveInterests()
        }
            return rootView
    }

    private fun saveInterests() {
        val currentUser = auth.currentUser
        val docRef = db.collection("userData").document(currentUser!!.uid)
        val userInterests = hashMapOf<String, String>()
        var count = 0

        if (checkBoxWildlife.isChecked) {
            count++
            userInterests["interest$count"] = "Wildlife"
        }
        if (checkBoxTravel.isChecked) {
            count++
            userInterests["interest$count"] = "Travel"
        }
        if (checkBoxFood.isChecked) {
            count++
            userInterests["interest$count"] = "Food"
        }
        if (checkBoxSocializing.isChecked) {
            count++
            userInterests["interest$count"] = "Socializing"
        }
        if (checkBoxBooks.isChecked) {
            count++
            userInterests["interest$count"] = "Books"
        }
        if (checkBoxGames.isChecked) {
            count++
            userInterests["interest$count"] = "Games"
        }
        if (checkBoxNetflix.isChecked) {
            count++
            userInterests["interest$count"] = "Netflix"
        }
        when {
            count > 6 -> showToast("Max 6 interests allowed!")

            count <= 0 -> showToast("Please select at least 1 interest!")

            else -> {
                docRef.collection("interests").document("interestlist")
                    .set(userInterests)
                    .addOnSuccessListener {
                        val aboutme = aboutMeEditText.text.toString()
                        if (aboutme.isNotEmpty()) {
                            docRef.update("aboutme", aboutme)
                                .addOnSuccessListener {
                                    dismiss()
                            }
                        }
                        showToast("Updated interest list")
                        dismiss()
                }
            }
        }
    }

    private fun showToast(toastMessage: String) {
        val toast = Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}


