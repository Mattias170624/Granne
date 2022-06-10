package com.example.granne.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.granne.adapter.ChatRecyclerAdapter
import com.example.granne.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var nicknameList = mutableListOf<String>()
    private var userUidList = mutableListOf<String>()

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)
        auth = Firebase.auth

        getAllMatchedUsers()
    }

    private fun getAllMatchedUsers() {
        val docRefSecondUser = db.collection("userData").document(auth.currentUser!!.uid)
            .collection("matchedUsers")

        docRefSecondUser.get()
            .addOnSuccessListener { result ->
                // Shows all our matched users in the RecycleView
                if (result.isEmpty) {
                    Toast.makeText(applicationContext,
                        "You have no active chats!",
                        Toast.LENGTH_SHORT).show()

                } else {
                    for (document in result) {
                        Log.d("!", "Matched users uid > ${document.id} ")

                        // For each matched user get their nickname
                        docRefSecondUser.document(document.id)
                            .get()
                            .addOnSuccessListener { name ->
                                val nickname = name.data!!.getValue("nickname").toString()

                                addToList(nickname, document.id)
                            }
                    }
                }
            }
    }

    private fun addToList(nickname: String, userUid: String) {
        recyclerView = findViewById(R.id.chatRecycleView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ChatRecyclerAdapter(nicknameList, userUidList)
        nicknameList.add(nickname)
        userUidList.add(userUid)
    }

}