package com.receipesta.receipefinderapp.user_interface.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.receipesta.receipefinderapp.databinding.ActivityRegisterPageBinding
import com.receipesta.receipefinderapp.user_interface.login.LoginPage
import com.google.firebase.auth.FirebaseAuth

class RegisterPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityRegisterPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializon firebase
        auth = FirebaseAuth.getInstance()
        //Listen the sign up button if it is clicked call createUser function
        binding.containedButton.setOnClickListener{
            createUser()
        }
    }

    /*
    *Take necessary values from edit texts
    * Check if they are empty
    * İf empty give an error to user to say it cannot be empty
    * İf not create a user in database
    * Operation is successful start the LoginPage for user to log in
    * İf it is not successful give user a toast message
    *
     */
    private fun createUser() {
        val username = binding.usernameEditText.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if(TextUtils.isEmpty(email)){
            binding.emailEditText.error = "Email cannot be empty"
            binding.emailEditText.requestFocus()
        }
        else if(TextUtils.isEmpty(password)){
            binding.passwordEditText.error = "Password cannot be empty"
            binding.passwordEditText.requestFocus()
        }
        else {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"User registered successfully",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginPage::class.java))
                }
                else {
                    Toast.makeText(this,"Registration error:  " + task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}