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
    var userInterests = mutableListOf<String>()
    var persons = mutableListOf<PersonFindMatch>()
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
            //val dialog = InterestDialogFragment()
            //dialog.show(supportFragmentManager, "interestdialog")
            Log.d("!", "Test")

            db.collection("userData")
                .get()
                .addOnSuccessListener { documents ->
                    allUsersUid.clear()
                    for (user in documents) {
                        allUsersUid.add(user.id)
                        //Log.d("!", "${user.id} cxxxxx=> ${user.data}")
                    }
                    checkForSameInterests()
                }

            /*  db.collection("userData").document("5hxBE047RdNvV40vlVOlvXtQWNC3")
                  .collection("interests").document("interestlist")
                  .get()
                  .addOnSuccessListener { documents ->
                      Log.d("!", "$documents")

                      if (documents.contains("interest")) {
                          Log.d("!", "contains!")
                      }


                  }
                  .addOnFailureListener { e ->
                      Log.d("!", "Error:", e)
                  }

             */


            /*docRef.collection("interests").document("interestlist").get()
                .addOnSuccessListener { document ->

                    Log.d("!","$document")

                    if (document.contains("travel")) {
                        Log.d("!","Yes")
                    } else Log.d("!","No")


                    //Log.d("!", "vvvvvvvvvvvvvvvvvvv ${document["interest1"]}")

                }

             */

            //Log.d("!",">> $docRef")
            //for (document in docRef.toString()) {
            //   Log.d("!",">> $document")

            //}

        }

        searchMatchButton.setOnClickListener {
            recyclerView.isVisible = true //

            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Hur man lägger till sina egna intressen i userInterests:

            val docRefMyInterests = db.collection("userData")
                .document(currentUser!!.uid)
                .collection("interests")
                .document("interestlist")

            docRefMyInterests.get()
                .addOnSuccessListener { document ->
                    userInterests.clear()
                    for (item in document.data!!) {
                        userInterests.add(item.toString())
                    }
                    Log.d("!", "User intressen finns i en mutable list: \n $userInterests")
                }
                .addOnFailureListener { e ->
                    Log.d("!", "Error:", e)
                }


            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Hur man får ANDRAS intressen


        }

        recyclerView()
    }

    private fun checkForSameInterests() {
        for (userUID in allUsersUid) {
            Log.d("!", userUID)
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