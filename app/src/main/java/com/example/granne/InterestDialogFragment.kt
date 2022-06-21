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
    private lateinit var checkboxList :MutableList<CheckBox>
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
        val currentUser = auth.currentUser
        val docRef = db.collection("userData").document(currentUser!!.uid)
        aboutMeEditText = rootView.findViewById(R.id.aboutmeEditText)
        checkboxList[0]=rootView.findViewById(R.id.checkBox1)
        checkboxList[1]=rootView.findViewById(R.id.checkBox2)
        checkboxList[2]=rootView.findViewById(R.id.checkBox3)
        checkboxList[3]=rootView.findViewById(R.id.checkBox4)
        checkboxList[4]=rootView.findViewById(R.id.checkBox5)
        checkboxList[5]=rootView.findViewById(R.id.checkBox6)
        checkboxList[6]=rootView.findViewById(R.id.checkBox7)
        saveChangesButton = rootView.findViewById(R.id.saveChangesButton)

        saveChangesButton.setOnClickListener {
            val userInterests = hashMapOf<String, String>()
            var count = 0
            if (checkboxList[0].isChecked) {
                count++
                userInterests["interest$count"] = "Wildlife"
            }
            if (checkboxList[1].isChecked) {
                count++
                userInterests["interest$count"] = "Travel"
            }
            if (checkboxList[2].isChecked) {
                count++
                userInterests["interest$count"] = "Food"
            }
            if (checkboxList[3].isChecked) {
                count++
                userInterests["interest$count"] = "Socialising"
            }
            if (checkboxList[4].isChecked) {
                count++
                userInterests["interest$count"] = "Books"
            }
            if (checkboxList[5].isChecked) {
                count++
                userInterests["interest$count"] = "Games"
            }
            if (checkboxList[6].isChecked) {
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

        return rootView
    }

    private fun showToast(toastMessage: String) {
        val toast = Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}


