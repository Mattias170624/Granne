package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.ImageButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    lateinit var nicknameUnderIcon: TextView
    lateinit var buttonFindMatch: ImageButton
    lateinit var buttonOptions: ImageButton
    lateinit var buttonChat: ImageButton
    lateinit var buttonInfo: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth = Firebase.auth


        nicknameUnderIcon = findViewById(R.id.nicknameUnderIcon)
        buttonFindMatch = findViewById(R.id.buttonFindMatch)
        buttonOptions = findViewById(R.id.buttonOptions)
        buttonChat = findViewById(R.id.buttonChat)
        buttonInfo = findViewById(R.id.buttonInformation)


        buttonChat.setOnClickListener {
            val chatIntent = Intent(this, ChatActivity::class.java)
            startActivity(chatIntent)
        }

        buttonOptions.setOnClickListener {
            val dialog = SettingsDialogFragment()
            dialog.show(supportFragmentManager, "optionsdialog")
        }

        buttonFindMatch.setOnClickListener {
            val findMatchIntent = Intent(this, FindMatchActivity::class.java)
            startActivity(findMatchIntent)
        }

        buttonInfo.setOnClickListener {
            val dialog = CustomDialogFragment()
            dialog.show(supportFragmentManager, "customDialog")
        }

    }

    override fun onStart() {
        super.onStart()

        // Listen for changes in nickname
        db.collection("userData").document(auth.currentUser!!.uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener // Stop listening to this snapshot
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("!", "Current data: ${snapshot.data}")
                    nicknameUnderIcon.text = snapshot.data!!.getValue("nickname").toString()
                } else {
                    Log.d("!", "Current data: null")
                }
            }
    }

    fun statsDialogButton(view: View) {
        val statsDialogFragment = StatsDialogFragment()
        statsDialogFragment.show(supportFragmentManager, "statsDialog")
    }

    override fun onBackPressed() { // When user presses back, application closes
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
