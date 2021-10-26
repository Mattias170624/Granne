package com.example.granne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    lateinit var spinner : Spinner
    lateinit var locale : Locale

    private var currentLanguage = "en"
    private var currentLang : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        title = "Granne"
        currentLanguage = intent.getStringExtra(currentLang).toString()
        spinner = findViewById(R.id.spinner)

        val list = ArrayList<String> ()
        list.add("Select language")
        list.add("English")
        list.add("Svenska")

        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when(position) {
                    0 -> {

                    }
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
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            val refresh = Intent(this, MainActivity::class.java)
            refresh.putExtra(currentLang, localeName)
            startActivity(refresh)
        } else {
            Toast.makeText(this@MainActivity, "Language, , already, , selected)!", Toast.LENGTH_SHORT).show();
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
        Log.d("!Main", "Pressed login button")

        // När man trycker på login knappen på första sidan så ska programmet kolla om
        // switchen "keep me logged in" == true. och då ska man skickas direkt till mainscreen
        // när man trycker på "Login" knappen

        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
    }

    fun startCreateAccount(view: View) {
        val createAccountIntent = Intent(this, CreateAccountActivity::class.java)
        startActivity(createAccountIntent)
    }


}


