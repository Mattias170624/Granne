package com.example.granne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatRoomActivity : AppCompatActivity() {

    data class Message(val name: String? = null, val text: String? = null)

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        auth = Firebase.auth

        val secondUserNickname: String = intent.getStringExtra("secondUserNickname").toString()
        val secondUserUid: String = intent.getStringExtra("secondUserUid").toString()
        getChatChannel(secondUserNickname, secondUserUid)


        //Log.d("!", "Chatting with: $secondUserNickname")
        //Log.d("!", "The other persons uid: $secondUserUid")

    }

    private fun getChatChannel(secondUserNickname: String, secondUserUid: String) {
        val docRef = db.collection("userData").document(auth.currentUser!!.uid)

        var myNickname: String
        val chatKey = auth.currentUser!!.uid.plus(secondUserUid)
        val selectedChatRoom = db.collection("chatRooms").document(chatKey)


        //val message = Message("$myNickname", "Hejsaan")

        Log.d("!", "<<<<<<<<<<<<< $chatKey")

        docRef.get()
            .addOnSuccessListener { documents ->
                myNickname = documents.data!!.getValue("nickname").toString()

                val message = Message(myNickname, "Hejsaan")



                selectedChatRoom.get()
                    .addOnSuccessListener { chatRoom ->
                        if (chatRoom.exists()) {
                            Log.d("!", "CHAT EXISTS!")
                            Log.d("!", "Chats with this user: ${chatRoom.data}")

                        } else {
                            Log.d("!", "No chat started with this user, creating one now")
                            db.collection("chatRooms").document(chatKey).set(message)
                        }
                    }


                //db.collection("chatRooms").document()
                //Kolla om det finns ett document med bådas uid tsm

                //om inte, gör ett nytt document

            }


    }
}