package com.receipesta.receipefinderapp.user_interface.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.receipesta.receipefinderapp.databinding.ActivityLoginPageBinding
import com.receipesta.receipefinderapp.user_interface.home.HomePage
import com.receipesta.receipefinderapp.user_interface.register.RegisterPage
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityLoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializon firebase
        auth = FirebaseAuth.getInstance()

        //To listen Log In Button and when it is clicked call the loginUser Function
        binding.containedButton.setOnClickListener{
            loginUser()
        }
        //When presses the Sign Up Button start the RegisterPage Activity using explicit intent
        binding.textButton.setOnClickListener {
            startActivity(Intent(this, RegisterPage::class.java))
        }
    }

    /*
    *First take the email and password value from our edit text
    * Then check if they are empty if so give an error to the user
    * İf not try to sign in with email and password and if operation is successfull start the HomePage Activity
    * Operation is not successful then give a toast message to the user with the exception
     */
    private fun loginUser(){
        val email = binding.emailOrUsernameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if(TextUtils.isEmpty(email)){
            binding.emailOrUsernameEditText.error = "Email cannot be empty"
            binding.emailOrUsernameEditText.requestFocus()
        }
        else if(TextUtils.isEmpty(password)){
            binding.passwordEditText.error = "Password cannot be empty"
            binding.passwordEditText.requestFocus()
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    Toast.makeText(this,"User logged in successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomePage::class.java))
                }
                else{
                    Toast.makeText(this,"Log in error:  " + task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}