package com.example.granne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
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
        getUserData()

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
                    chatDocRef.set(chatInfo)
                        .addOnSuccessListener {
                            updateChatUi()
                        }
                }
                if (task.exists()) {
                    updateChatUi()
                }
            }
    }
    private fun getUserData(){
        val secondUserUid: String = intent.getStringExtra("secondUserUid").toString()
        checkExist(secondUserUid)
    }
    private fun checkExist(secondUserUid: String ) {
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

    private fun updateChatUi() {
        val chatDocRef = db.collection("chatRooms").document(chatKey)
        // Sets title for the chatroom
        userTitle.text = secondUserNickname

        chatDocRef.get()
            .addOnSuccessListener { list ->
                val oldList = list.data!!.getValue("messagelist").toString()
                messageList.add(oldList)
                // add $messageList to view
                messageButton.setOnClickListener {
                    val text = "$myNickname: ${newMessageEditText.text}"
                    messageList.add(text)
                    newMessageEditText.text.clear()
                    // add $text to view
                    chatDocRef.update("messagelist", messageList)
                }
            }
        snapshotValidation()
    }

    private fun snapshotValidation(){
        val chatDocRef = db.collection("chatRooms").document(chatKey)
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
                }
            }
    }
}



