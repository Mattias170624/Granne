package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var nicknameUnderIcon: TextView
    lateinit var buttonOptions: ImageButton
    lateinit var buttonFindMatch: Button
    lateinit var buttonChat: ImageButton

    val db = Firebase.firestore
    // Use cloud database to add additional information to user later on
    // Ex: Interests, name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth = Firebase.auth

        val currentUser = auth.currentUser

        nicknameUnderIcon = findViewById(R.id.nicknameUnderIcon)
        buttonOptions = findViewById(R.id.buttonOptions)
        buttonFindMatch = findViewById(R.id.buttonFindMatch)
        buttonChat = findViewById(R.id.buttonChat)
        nicknameUnderIcon.text = currentUser!!.email


        buttonChat.setOnClickListener {
            val chatIntent = Intent(this, ChatActivity::class.java)
            startActivity(chatIntent)
        }

        buttonOptions.setOnClickListener {
            var dialog = SettingsDialogFragment()
            dialog.show(supportFragmentManager, "optionsdialog")
        }

        buttonFindMatch.setOnClickListener {
            val findMatchIntent = Intent(this, FindMatchActivity::class.java)
            startActivity(findMatchIntent)
        }

        var infoImageButton = findViewById<ImageButton>(R.id.buttonInformation)
        infoImageButton.setOnClickListener {

            var dialog = CustomDialogFragment()

            dialog.show(supportFragmentManager, "customDialog")
        }

    }

    override fun onBackPressed() { // When user presses back, application closes
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun statsDialogButton(view: View) {
        var statsDialogFragment = StatsDialogFragment()
        statsDialogFragment.show(supportFragmentManager, "statsDialog")
    }

}
