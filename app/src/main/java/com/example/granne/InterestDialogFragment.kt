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
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.HashMap

class InterestDialogFragment : DialogFragment() {

    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox
    private lateinit var checkBox5: CheckBox
    private lateinit var checkBox6: CheckBox
    private lateinit var checkBox7: CheckBox
    lateinit var saveChangesButton: Button

    lateinit var auth: FirebaseAuth

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_interest_dialog, container, false)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        checkBox1 = rootView.findViewById(R.id.checkBox1)
        checkBox2 = rootView.findViewById(R.id.checkBox2)
        checkBox3 = rootView.findViewById(R.id.checkBox3)
        checkBox4 = rootView.findViewById(R.id.checkBox4)
        checkBox5 = rootView.findViewById(R.id.checkBox5)
        checkBox6 = rootView.findViewById(R.id.checkBox6)
        checkBox7 = rootView.findViewById(R.id.checkBox7)
        saveChangesButton = rootView.findViewById(R.id.saveChangesButton)

        saveChangesButton.setOnClickListener {
            val docRef = db.collection("userData").document(currentUser!!.uid)
            val userInterests = hashMapOf<String, String>()
            var count = 0

            if (checkBox1.isChecked) {
                count++
                userInterests["interest$count"] = "Wildlife"
            }
            if (checkBox2.isChecked) {
                count++
                userInterests["interest$count"] = "Travel"
            }
            if (checkBox3.isChecked) {
                count++
                userInterests["interest$count"] = "Food"
            }
            if (checkBox4.isChecked) {
                count++
                userInterests["interest$count"] = "Socialising"
            }
            if (checkBox5.isChecked) {
                count++
                userInterests["interest$count"] = "Books"
            }
            if (checkBox6.isChecked) {
                count++
                userInterests["interest$count"] = "Games"
            }
            if (checkBox7.isChecked) {
                count++
                userInterests["interest$count"] = "Netflix"
            }

            when {
                count > 6 -> showToast("Max 6 interests allowed!")

                count <= 0 -> showToast("Please select at least 1 interest!")

                else -> {
                    // Deletes old list and then adds a new one
                    docRef.update("interests", FieldValue.delete())
                        .addOnSuccessListener {
                            Log.d("!", "Successfully deleted old list")
                            docRef.update("interests", FieldValue.arrayUnion(userInterests))
                                .addOnSuccessListener {
                                    dismiss()
                                    showToast("Successfully updated your interests!")
                                }
                        }
                }
            }
        }

        return rootView
    }


    private fun updateUi() {
    }

    private fun showToast(toastMessage: String) {
        val toast = Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}


