package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var spinner: Spinner
    lateinit var locale: Locale
    private var currentLanguage = "en"
    private var currentLang: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) { // Check if user is signed in
            startActivity(Intent(this, HomeActivity::class.java))
        }
        title = "Granne"
        currentLanguage = intent.getStringExtra(currentLang).toString()
        spinner = findViewById(R.id.spinner)
        val listLanguage = ArrayList<String>()
        listLanguage.add("Language")
        listLanguage.add("English")
        listLanguage.add("Svenska")
        listLanguageadapterfun(listLanguage)
    }

    private fun listLanguageadapterfun(listLanguage :ArrayList<String>){
        val listLanguageAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listLanguage)
        listLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = listLanguageAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long,
            ) {
                when (position) {
                    1 -> setLocale("en")
                    2 -> setLocale("sv")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setLocale(localeName: String) {
        if (localeName != currentLanguage) {
            locale = Locale(localeName)
            val res = resources
            val displayMetrics = res.displayMetrics
            val config = res.configuration
            config.locale = locale
            res.updateConfiguration(config, displayMetrics)
            val refresh = Intent(this, MainActivity::class.java)
            refresh.putExtra(currentLang, localeName)
            startActivity(refresh)
        } else {
            Toast.makeText(
                this@MainActivity,
                "Language already selected!",
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
        exitProcess(0)
    }

    fun startLoginActivity(view: View) {
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
    }

    fun startCreateAccount(view: View) {
        val createAccountIntent = Intent(this, CreateAccountActivity::class.java)
        startActivity(createAccountIntent)
    }
}


