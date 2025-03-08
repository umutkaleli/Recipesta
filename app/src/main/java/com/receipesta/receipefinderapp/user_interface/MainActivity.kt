package com.receipesta.receipefinderapp.user_interface

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.receipesta.receipefinderapp.R
import com.receipesta.receipefinderapp.user_interface.home.HomePage
import com.receipesta.receipefinderapp.user_interface.login.LoginPage
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Implementing firebase to this Activity
        auth = FirebaseAuth.getInstance()
    }

    /*
    * When app started if user logged in before it starts the HomePage
    * When app started if user did not log in it starts the LoginPage for user to log in
     */
    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user == null) {
            startActivity(Intent(this@MainActivity, LoginPage::class.java))
        }
        else{
            startActivity(Intent(this@MainActivity, HomePage::class.java))
        }
    }
}