package com.sabanci.ovatify

import TokenManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.api.RetrofitClient3

class SignInActivity : AppCompatActivity() {


    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_page)
        //TokenManager.initialize(this)





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
                            Log.d("call details currentUser", user.toString())
                            var userToken = user?.getIdToken(false)?.result?.token
                            Log.d("call details", userToken.toString())

                            TokenManager.userToken = userToken.toString()

                            //val retrofitCli = RetrofitClient(userToken.toString())

                            userToken = "Bearer $userToken"



                            Log.d("call details", userToken)
                            Log.d("Auth Token Before Change", RetrofitClient.AUTH_TOKEN)
                            //RetrofitClient.AUTH_TOKEN = userToken.toString()
                            Log.d("call details", RetrofitClient.AUTH_TOKEN)

                            Log.d("Auth Token Before Sleep", RetrofitClient.AUTH_TOKEN)
                            Thread.sleep(500)
                            Log.d("Auth Token After Sleep", RetrofitClient.AUTH_TOKEN)

                            //val call = RetrofitClient.apiService.login(userToken)

                            val call = RetrofitClient.apiService.login()


                            call.enqueue(object : Callback<Response<Void>>{
                                override fun onResponse(
                                    call: Call<Response<Void>>,
                                    response: Response<Response<Void>>
                                ) {
                                    if(response.isSuccessful)
                                    {
                                        Log.d("Login Api Call", "Api Call Successful, Response Code: ${response.code()}")
                                        //TokenManager.updateUserToken(userToken)
                                        //RetrofitClient.AUTH_TOKEN = userToken.toString()
                                        Log.d("Current Token", "Current Token: ${RetrofitClient.AUTH_TOKEN}")

                                        val intent = Intent(this@SignInActivity, HomePageActivity::class.java)
                                        startActivity(intent)
                                    }

                                    else
                                    {
                                        Log.e("Login Api Call", "Api Call Failed, Response Code: ${response.code()}")
                                        //RetrofitClient.AUTH_TOKEN = ""
                                        Toast.makeText(this@SignInActivity, "Cannot Sign In, Error Code ${response.code()}", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<Response<Void>>, t: Throwable) {
                                    TODO("Not yet implemented")
                                }


                            })


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