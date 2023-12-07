package com.sabanci.ovatify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.sabanci.ovatify.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {


    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_page)





        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener {
            val email = findViewById<TextView>(R.id.signInEmailInner).text.toString()
            val password = findViewById<TextView>(R.id.signInPasswordInner).text.toString()

            if (email.isNotEmpty())
            {
                if (password.isNotEmpty())
                {
                    firebaseAuth = FirebaseAuth.getInstance()
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

                        if (it.isSuccessful)
                        {
                            val user = FirebaseAuth.getInstance().currentUser
                            var userToken = user?.getIdToken(false)?.result?.token
                            userToken = "Bearer $userToken"
                            val call = RetrofitClient.apiService.login(userToken)

                            call.enqueue(object : Callback<Response<Void>>{
                                override fun onResponse(
                                    call: Call<Response<Void>>,
                                    response: Response<Response<Void>>
                                ) {
                                    if(response.isSuccessful)
                                    {
                                        Log.d("Login Api Call", "Api Call Successful, Response Code: ${response.code()}")


                                    }

                                    else
                                    {
                                        Log.e("Login Api Call", "Api Call Failed, Response Code: ${response.code()}")
                                    }
                                }

                                override fun onFailure(call: Call<Response<Void>>, t: Throwable) {
                                    TODO("Not yet implemented")
                                }


                            })

                            val intent = Intent(this, HomePageActivity::class.java)
                            startActivity(intent)
                        }

                        else
                        {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
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


        val dontHaveAnAccountText = findViewById<TextView>(R.id.textView5)
        dontHaveAnAccountText.setOnClickListener{
            val signUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }
    }
}