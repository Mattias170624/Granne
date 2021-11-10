package com.example.granne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ChatRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val secondUserNickname: String = intent.getStringExtra("secondUserNickname").toString()
        val secondUserUid: String = intent.getStringExtra("secondUserUid").toString()

        Log.d("!", "Chatting with: $secondUserNickname")
        Log.d("!", "The other persons uid: $secondUserUid")

    }
}