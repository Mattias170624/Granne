package com.example.granne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class ChatActivity : AppCompatActivity() {
    lateinit var buttonSendMessage: Button
    lateinit var edit_name: EditText
    lateinit var edit_message: EditText
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
        realtimeUpdateListener()
        textDisplay = findViewById(R.id.textDisplay)
        buttonSendMessage = findViewById(R.id.buttonSendMessage)
        edit_name = findViewById(R.id.edit_name)
        edit_message = findViewById(R.id.edit_message)
        buttonSendMessage.setOnClickListener{
            sendMessage()
        }
    }

    private fun sendMessage(){
        val newMessage = mapOf(
            NAME_FIELD to edit_name.text.toString(),
            TEXT_FIELD to edit_message.text.toString())
        firestoreChat.set(newMessage).addOnSuccessListener({
            Toast.makeText(this@ChatActivity, "Message Sent", Toast.LENGTH_SHORT).show()
        }).addOnFailureListener{ e -> e.message?.let { Log.e("ERROR", it) } }

        }
    private fun realtimeUpdateListener(){
        firestoreChat.addSnapshotListener { documentSnapshot, e ->
            when{
                e != null -> e.message?.let { Log.e("ERROR", it) }
                documentSnapshot != null && documentSnapshot.exists() -> {
                    with(documentSnapshot) {
                        if (data != null)
                            textDisplay.setText( "${data!![NAME_FIELD]}:${data!![TEXT_FIELD]}")
                    }
                }
            }

        }
    }
        }




