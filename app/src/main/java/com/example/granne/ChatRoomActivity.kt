package com.example.granne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        auth = Firebase.auth

        secondUserNickname = intent.getStringExtra("secondUserNickname").toString()
        val secondUserUid: String = intent.getStringExtra("secondUserUid").toString()
        chatKey = auth.currentUser!!.uid.plus(secondUserUid)

        getChatChannel(chatKey)


        //Log.d("!", "Chatting with: $secondUserNickname")
        //Log.d("!", "The other persons uid: $secondUserUid")

    }

    private fun getChatChannel(chatKey: String) {
        val allChatRefs = db.collection("chatRooms")
        val myDocRef = db.collection("userData").document(auth.currentUser!!.uid)
        val txt = mutableListOf<String>()
        txt.add("Heeej")

        myDocRef.get()
            .addOnSuccessListener { documents ->
                myNickname = documents.data!!.getValue("nickname").toString()

                allChatRefs.get()
                    .addOnSuccessListener { rooms ->

                        for (chat in rooms) {
                            when {
                                !chat.exists() -> {
                                    Log.d("!", "No key found but created one now")
                                    updateUi(chatKey, false)
                                }

                                chat.exists() -> {
                                    if (chat.id.contains(chatKey)) {
                                        Log.d("!","Key found")
                                        updateUi(chatKey, true)
                                        break
                                    }
                                    if (!chat.id.contains(chatKey)) {
                                        updateUi(chatKey, false)
                                        break
                                    }
                                }
                            }
                        }

                        /*
                         else {

                             // If chatRooms is filled with documents, get the document that contains the right chatKey
                             for (room in rooms) {

                                 if (room.id.contains(chatKey)) {
                                     Log.d("!", "Chat exists!")
                                     Log.d("!", "Texts inside this room: ${room.data}")


                                     // If chatRooms does not contain our chatKey, then create a chat
                                 } else {
                                     Log.d("!", "No chatroom with this chatKey existed")
                                     Log.d("!", "Creating one now with key: $chatKey")
                                     getRoomContent(chatKey, myNickname)
                                 }
                             }
                         }

                         */

                    }
            }
    }

    private fun updateUi(chatKey: String, exists: Boolean) {
        val chatDocRef = db.collection("chatRooms")
        val chatInfo = hashMapOf(
            "user" to secondUserNickname
        )

        when {
            !exists -> {
                chatDocRef.document(chatKey).set(chatInfo)
                    .addOnSuccessListener {
                        Log.d("!", "Added new chat with user: $secondUserNickname")
                        updateUi(chatKey, true)
                    }
            }

            exists -> {
                chatDocRef.document(chatKey).get()
                    .addOnSuccessListener { messages ->
                        for (text in messages.data!!) {
                            Log.d("!", "Texts in this chat: $text")

                        }
                    }
            }
        }
    }

    private fun getRoomContent(chatKey: String, myNickname: String) {
        db.collection("chatRooms").document(chatKey)
            .set(Message(myNickname, "Hej jag är snart en EditText"))
            .addOnSuccessListener {
                Log.d("!", "Created room content with a new message")
            }
    }
}


/*
    fun getRoomKey(secondUserUid: String, secondUserNickname: String, myNickname: String) {
        val chatKey = auth.currentUser!!.uid.plus(secondUserUid)

        db.collection("chatRooms").get()
            .addOnSuccessListener { rooms ->
                for (room in rooms) {
                    if (room.contains(chatKey)) {
                        //updateRoomWithMessages(chatKey, myNickname)
                        val message = Message("myNickname", "Hejsaan")
                        Log.d("!", "CHAT EXISTS with $secondUserNickname! $secondUserUid")
                        Log.d("!", "Chats with this user: ${room.data}")
                    } else {
                        Log.d("!", "no foundo")
                    }
                }
            }

        val selectedChatRoom = db.collection("chatRooms").document()


    }

    fun updateRoomWithMessages(chatKey: String, myNickname: String) {
        val message = Message(myNickname, "Hej")

        db.collection("chatRooms").document(chatKey)
            .set(message)
            .addOnSuccessListener {
                Log.d("!", "eee")
            }
    }
*/

