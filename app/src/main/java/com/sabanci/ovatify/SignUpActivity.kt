package com.sabanci.ovatify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_page)

        firebaseAuth = FirebaseAuth.getInstance()

        val signUpButton = findViewById<Button>(R.id.signInButton)
        signUpButton.setOnClickListener{
            val email = findViewById<TextView>(R.id.signUpEmailInner).text.toString()
            val password = findViewById<TextView>(R.id.signUpPasswordInner).text.toString()
            val confirmPass = findViewById<TextView>(R.id.signUpPasswordInnerAgain).text.toString()

            if (email.isNotEmpty())
            {
                if (password.isNotEmpty())
                {
                    if (password == confirmPass)
                    {
                        if(password.length < 8 || password.length > 16)
                        {
                            Toast.makeText(this, "Password must be between 8 and 16 characters long!", Toast.LENGTH_SHORT).show()
                        }

                        else if (!password.matches(".*[A-Z].*".toRegex()))
                        {
                            Toast.makeText(this, "Password must contain at least one uppercase letter!", Toast.LENGTH_SHORT).show()
                        }

                        else if (!password.matches(".*[a-z].*".toRegex()))
                        {
                            Toast.makeText(this, "Password must contain at least one lowercase letter!", Toast.LENGTH_SHORT).show()
                        }

                        else if (!password.matches(".*[0-9].*".toRegex()))
                        {
                            Toast.makeText(this, "Password must contain at least one number!", Toast.LENGTH_SHORT).show()
                        }

                        else if (!password.matches(".*[.-;].*".toRegex()))
                        {
                            Toast.makeText(this, "Password must contain at least one of . , - or ;!", Toast.LENGTH_SHORT).show()
                        }

                        else
                        {
                            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                                if (it.isSuccessful)
                                {
                                    val intent = Intent(this, SignInActivity::class.java)
                                    startActivity(intent)
                                }
                                else
                                {
                                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    }

                    else
                    {
                        Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                    }
                }

                else
                {
                    Toast.makeText(this, "Password field cannot be empty!", Toast.LENGTH_SHORT).show()
                }
            }

            else
            {
                Toast.makeText(this, "Email field cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }

        val alreadyHaveAccount = findViewById<TextView>(R.id.alreadyHaveAccountTextView)
        alreadyHaveAccount.setOnClickListener{
            val signInIntent = Intent(this, SignInActivity::class.java)
            startActivity(signInIntent)
        }
    }
}