package com.example.granne.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.granne.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatRoomActivity : AppCompatActivity() {

    data class Message(val name: String? = null, val text: String? = null)


    private lateinit var auth: FirebaseAuth
    private lateinit var myNickname: String
    val db = Firebase.firestore

    private lateinit var chatKey: String
    private lateinit var secondUserNickname: String
    private lateinit var newMessageEditText: EditText
    private lateinit var messageTextView: TextView
    private lateinit var messageButton: Button
    private lateinit var userTitle: TextView
    lateinit var messageList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        auth = Firebase.auth

        messageTextView = findViewById(R.id.messageTextView)
        newMessageEditText = findViewById(R.id.newMessageEditText)
        messageButton = findViewById(R.id.messageButton)
        userTitle = findViewById(R.id.userTitle)

        secondUserNickname = intent.getStringExtra("secondUserNickname").toString()
        val secondUserUid: String = intent.getStringExtra("secondUserUid").toString()
        val myDocRef = db.collection("userData").document(auth.currentUser!!.uid)


        myDocRef.get()
            .addOnSuccessListener { name ->
                myNickname = name.data!!.getValue("nickname").toString()

                myDocRef.collection("matchedUsers").document(secondUserUid).get()
                    .addOnSuccessListener { documents ->
                        chatKey = documents.data!!.getValue("chatId").toString()
                        createChatChannel(chatKey)
                    }
            }

    }

    private fun createChatChannel(chatKey: String) {
        val chatDocRef = db.collection("chatRooms").document(chatKey)
        messageList = arrayListOf()

        val chatInfo = hashMapOf(
            "user1" to secondUserNickname,
            "user2" to myNickname,
            "messagelist" to messageList
        )

        chatDocRef.get()
            .addOnSuccessListener { task ->
                if (!task.exists()) {
                    Log.d("!", "No chat with the key: $chatKey")

                    chatDocRef.set(chatInfo)
                        .addOnSuccessListener {
                            Log.d("!", "Created chat with $secondUserNickname")
                            updateChatUi()
                        }
                }
                if (task.exists()) {
                    Log.d("!", "Joining chat with $secondUserNickname with chatId: $chatKey")
                    updateChatUi()
                }
            }

    }

    private fun updateChatUi() {
        val chatDocRef = db.collection("chatRooms").document(chatKey)


        // Sets title for the chatroom
        val titleNickname = secondUserNickname
        userTitle.text = titleNickname

        chatDocRef.get()
            .addOnSuccessListener { list ->
                val oldList = list.data!!.getValue("messagelist").toString()
                messageList.add(oldList)

                // add $messageList to view
                Log.d("!","new list$messageList")

                messageButton.setOnClickListener {
                    val text = "$myNickname: ${newMessageEditText.text}"
                    messageList.add(text)
                    newMessageEditText.text.clear()

                    // add $text to view
                    Log.d("!","Sent text: $text")

                    chatDocRef.update("messagelist", messageList)


                }
            }

        chatDocRef
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener // Stop listening to this snapshot
                }

                if (snapshot != null && snapshot.exists()) {
                    val currentList = snapshot.data!!.getValue("messagelist")
                    if (currentList.toString().isNotEmpty()) {

                        messageTextView.text = currentList.toString()
                            .replace("]", "")
                            .replace("[", "")
                            .replace(",", "")

                    }

                } else {
                    Log.d("!", "Current data: null")
                }

            }


    }
}



