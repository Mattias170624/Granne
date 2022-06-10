package com.example.granne.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.granne.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StatsDialogFragment : DialogFragment() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        auth = Firebase.auth

        val rootView: View = inflater.inflate(R.layout.stats_dialog_fragment, container, false)
        val docRef = db.collection("userData").document(auth.currentUser!!.uid)

        val nickname = rootView.findViewById<TextView>(R.id.nicknameText)
        val email = rootView.findViewById<TextView>(R.id.emailText)
        val aboutMe = rootView.findViewById<TextView>(R.id.aboutMeText)
        val matchedUsers = rootView.findViewById<TextView>(R.id.matchedUsers)

        docRef.get()
            .addOnSuccessListener { documents ->
                nickname.text = "Nickname: ${documents.data!!.getValue("nickname")}"
                email.text = "Email: ${documents.data!!.getValue("email")}"
                aboutMe.text = "About me: ${documents.data!!.getValue("aboutme")}"

                docRef.collection("matchedUsers").get()
                    .addOnSuccessListener { usersMatched ->
                        val matchedList = mutableListOf<String>()

                        for (user in usersMatched) {
                            val matchedUserNickname = user.getString("nickname")

                            if (matchedUserNickname != null) {
                                matchedList.add(matchedUserNickname)
                            }
                        }
                        if (matchedList.isNotEmpty()) {
                            matchedUsers.text = "Active matches with: $matchedList"
                        }

                    }
            }

        return rootView
    }
}