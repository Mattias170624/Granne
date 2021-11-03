package com.example.granne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase

class FindMatchActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    lateinit var interestButton: Button
    lateinit var searchMatchButton: Button
    lateinit var recyclerView: RecyclerView
    var persons = mutableListOf<PersonFindMatch>()

    var userInterests = mutableListOf<String>()
    val allUsersUid = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_match)
        auth = Firebase.auth

        val currentUser = auth.currentUser

        interestButton = findViewById(R.id.interestButton)
        searchMatchButton = findViewById(R.id.searchMatchButton)
        recyclerView = findViewById(R.id.rwFindMatch)


        interestButton.setOnClickListener {
            val dialog = InterestDialogFragment()
            dialog.show(supportFragmentManager, "interestdialog")

        }

        searchMatchButton.setOnClickListener {
            recyclerView.isVisible = true

            db.collection("userData").document(auth.currentUser!!.uid)
                .collection("interests").document("interestlist")
                .get()

                .addOnSuccessListener { document ->
                    userInterests.clear()
                    for (item in document.data!!.values) {
                        userInterests.add(item.toString())
                    }

                    db.collection("userData")
                        .get()
                        .addOnSuccessListener { documents ->
                            Log.d("!", "My interests: $userInterests")
                            for (userID in documents) {
                              checkForSameInterests(userID.id)
                            }

                        }
                }
                .addOnFailureListener { e ->
                    Log.d("!", "Error:", e)
                }


/*
            db.collection("userData")
                .get()
                .addOnSuccessListener { documents ->
                    for (userID in documents) {
                        checkForSameInterests(userID.id)
                    }
                }

 */
        }

        recyclerView()
    }

    private fun checkForSameInterests(UID: String) {
        db.collection("userData").document(UID)
            .collection("interests").document("interestlist")
            .get()
            .addOnSuccessListener { documents ->

                Log.d("!", "Other users interests: ${documents.data!!.values}")

                val secondUserInterests = documents.data!!.values

                userInterests.forEachIndexed { index, value ->
                    if (secondUserInterests.contains(value)) {
                        Log.d("!", ">>> MATCH $value")
                    }


                }


                /*if (userInterests.containsAll(secondUserInterests)) {
                    Log.d("!","True")
                } else Log.d("!","False")

                 */


/*
                if (secondUserInterests.contains(userInterests.forEachIndexed { index, s ->
                        Log.d("!","Match $index /// $s")

                    })) {

                    //Log.d("!", "Match!")
                } else Log.d("!", "No Match!")

 */

                /*
                val interestCount =  documents.data!!.count()
                for (interest in 0..interestCount) {
                    Log.d("!",">>>>>>>>>>> ${documents.data!!.values}")
                }

                 */


                //if (documents.data!!.containsValue("Travel")){
                //    Log.d("!","FOUND FOUND FOUND FOUND")
                //}


            }


    }

    fun recyclerView() {

        recyclerView.layoutManager = LinearLayoutManager(this)

        persons.add(PersonFindMatch("testNamn", "Bio"))

        val adapter = PersonFindMatchRecycleViewAdapter(this, persons)
        recyclerView.adapter = adapter
        recyclerView.isVisible = false
    }

}

