package com.example.granne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    lateinit var buttonSendMessage: Button
    lateinit var nicknameTextView: TextView
    lateinit var messageEditText: EditText
    lateinit var textDisplay: TextView

    companion object {
        const val COLLECTION_KEY = "Chat"
        const val DOCUMENT_KEY = "Message"
        const val NAME_FIELD = "Name"
        const val TEXT_FIELD = "Text"
    }

    private val firestoreChat by lazy {
        FirebaseFirestore.getInstance().collection(COLLECTION_KEY).document(DOCUMENT_KEY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        auth = Firebase.auth

        textDisplay = findViewById(R.id.textDisplay)
        buttonSendMessage = findViewById(R.id.buttonSendMessage)
        nicknameTextView = findViewById(R.id.nicknameTextView)
        messageEditText = findViewById(R.id.messageEditText)
        nicknameTextView.text = "User"

        realtimeUpdateListener()

        buttonSendMessage.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val newMessage = mapOf(
            NAME_FIELD to nicknameTextView.text.toString(),
            TEXT_FIELD to messageEditText.text.toString()
        )
        firestoreChat.set(newMessage).addOnSuccessListener {
            Toast.makeText(this@ChatActivity, "Message Sent", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { e -> e.message?.let { Log.e("ERROR", it) } }
    }

    private fun realtimeUpdateListener() {
        firestoreChat.addSnapshotListener { documentSnapshot, e ->
            when {
                e != null -> e.message?.let { Log.e("ERROR", it) }
                documentSnapshot != null && documentSnapshot.exists() -> {
                    with(documentSnapshot) {
                        if (data != null)
                            textDisplay.text = "${data!![NAME_FIELD]}: ${data!![TEXT_FIELD]}"
                    }
                }
            }
        }
    }
}




