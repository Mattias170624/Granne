package com.example.granne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FindMatchActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    lateinit var interestButton: Button
    lateinit var searchMatchButton: Button
    lateinit var FindMatchRecyclerView: RecyclerView
    var listPersons = mutableListOf<PersonFindMatch>()
    lateinit var userLocation: Any
    var listUserInterests = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_match)
        auth = Firebase.auth
        interestButton = findViewById(R.id.interestButton)
        searchMatchButton = findViewById(R.id.searchMatchButton)
        FindMatchRecyclerView = findViewById(R.id.rwFindMatch)
        interestButton.setOnClickListener {
            val dialog = InterestDialogFragment()
            dialog.show(supportFragmentManager, "interestdialog")
            FindMatchRecyclerView.isVisible = false
        }
        findMatchUser()
    }
    private fun findMatchUser(){
        val currentUser = auth.currentUser
        searchMatchButton.setOnClickListener {
            val userDocRef = db.collection("userData").document(auth.currentUser!!.uid)
            userDocRef.get()
                .addOnSuccessListener { documents ->
                    userLocation = documents.data!!.getValue("location")
                    userDocRef.collection("interests").document("interestlist")
                        .get()
                        .addOnSuccessListener { document ->
                            if (document.data.isNullOrEmpty()) {
                                showToast("Add your interests before searching!")
                            } else {
                                listPersons.clear()
                                FindMatchRecyclerView.isVisible = true
                                listUserInterests.clear()
                                for (item in document.data!!.values) {
                                    listUserInterests.add(item.toString())
                                }
                                db.collection("userData")
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (userID in documents) {
                                            if (userID.id != currentUser!!.uid) // Removes being able to find yourself in interest search
                                                checkUserLocation(userID.id)
                                        }
                                    }
                            }
                        }
                }
        }
    }

    private fun checkUserLocation(UID: String) {
        db.collection("userData").document(UID)
            .get()
            .addOnSuccessListener { documents ->
                val secondUserLocation = documents.data!!.getValue("location")
                if (userLocation.toString() == secondUserLocation.toString()) checkUserInterests(UID)
            }
    }

    private fun checkUserInterests(UID: String) {
        db.collection("userData").document(UID)
            .collection("interests").document("interestlist")
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.data.isNullOrEmpty()) {
                    val secondUserInterests = documents.data!!.values
                    val sameInterestsList = mutableListOf<String>()
                    sameInterestsList.clear()
                    listUserInterests.forEachIndexed { index, value ->
                        if (secondUserInterests.contains(value)) {
                            sameInterestsList.add(value)
                        }
                    }
                    if (sameInterestsList.isNotEmpty()) {
                        val numOfInterests = sameInterestsList.size
                        showMatchedUsersInfo(UID,
                            secondUserInterests,
                            sameInterestsList,
                            numOfInterests)
                    }
                }
            }
    }

    private fun showMatchedUsersInfo(
        matchedUID: String,
        secondUserInterests: MutableCollection<Any>,
        sameInterestsList: MutableList<String>,
        numOfInterests: Int,
    ) {
        db.collection("userData").document(matchedUID).get()
            .addOnSuccessListener { document ->
                val matchedUsersNickname = document.data!!.getValue("nickname").toString()
                val matchedUsersAboutMe = document.data!!.getValue("aboutme").toString()
                val matchedUsersUid = document.data!!.getValue("uid").toString()
                addToList(matchedUsersNickname,
                    matchedUsersAboutMe,
                    secondUserInterests,
                    matchedUsersUid)
            }
    }

    private fun addToList(
        nickname: String,
        aboutMe: String,
        allInterests: MutableCollection<Any>,
        matchedUID: String,
    ) {
        FindMatchRecyclerView.layoutManager = LinearLayoutManager(this)
        FindMatchRecyclerView.adapter = PersonFindMatchRecycleViewAdapter(this, listPersons)

        listPersons.add(PersonFindMatch(nickname, aboutMe, allInterests, matchedUID))

    }

    private fun showToast(toastMessage: String) {
        val toast = Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}
