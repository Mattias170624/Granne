package com.example.granne

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StatsDialogFragment : DialogFragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var nickname: TextView
    private lateinit var email: TextView
    private lateinit var aboutMe: TextView
    private lateinit var matchedUsers: TextView
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        auth = Firebase.auth

        val rootView: View = inflater.inflate(R.layout.stats_dialog_fragment, container, false)
        nickname = rootView.findViewById<TextView>(R.id.nicknameText)
        email = rootView.findViewById<TextView>(R.id.emailText)
        aboutMe = rootView.findViewById<TextView>(R.id.aboutMeText)

        matchedUsers = rootView.findViewById<TextView>(R.id.matchedUsers)

        getUserData()

        return rootView
    }

    private fun getUserData() {
        db.collection("userData").document(auth.currentUser!!.uid).get()

            .addOnSuccessListener { documents ->
            nickname.text = "Nickname: ${documents.data!!.getValue("nickname")}"
            email.text = "Email: ${documents.data!!.getValue("email")}"
            aboutMe.text = "About me: ${documents.data!!.getValue("aboutme")}"

            db.collection("matchedUsers").get()
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
    }
}