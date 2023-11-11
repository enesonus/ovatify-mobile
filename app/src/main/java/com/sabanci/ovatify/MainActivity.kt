package com.sabanci.ovatify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_page)


        val signUpButton = findViewById<Button>(R.id.sign_up_button)
        val signInButton = findViewById<Button>(R.id.sign_in_button)

        signUpButton.setOnClickListener {

            val signUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpIntent)

            //Toast.makeText(this, "Clicked sign up button!", Toast.LENGTH_SHORT).show()
        }

        signInButton.setOnClickListener {

            val signInIntent = Intent(this, SignInActivity::class.java)
            startActivity(signInIntent)

            //Toast.makeText(this, "Clicked sign in button!", Toast.LENGTH_SHORT).show()
        }
    }
}