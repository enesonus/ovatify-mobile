package com.sabanci.ovatify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.api.RetrofitClient.apiService
import com.sabanci.ovatify.data.CreateUserRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    var responseMessage = MutableLiveData<String>()


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

                        val hasSpecialChar = password.any { it in setOf('.', '-', ';') }

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


                        else if(!hasSpecialChar)
                        {
                            Toast.makeText(this, "Password must contain at least one of . , - or ;!", Toast.LENGTH_SHORT).show()
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

                                    val user = FirebaseAuth.getInstance().currentUser
                                    val email = user?.email
                                    var userToken = user?.getIdToken(false)?.result?.token
                                    userToken = "Bearer $userToken"
                                    Log.d("The Token", "Firebase Authentication Token: $userToken , The Email: $email")
                                    //val request = CreateUserRequest(userEmail, userToken)


                                    val call = apiService.createUser(userToken, email)

                                    call.enqueue(object : Callback<Response<Void>> {
                                        override fun onResponse(call: Call<Response<Void>>, response: Response<Response<Void>>) {

                                            Log.d("ApiCall", "HTTP Response Code: ${response.code()}")

                                            if (response.isSuccessful) {
                                                // API call successful
                                                // You can also access other information such as response.body(), headers, etc.
                                                //val responseBody = response.body()
                                                //val headers = response.headers()

                                                if (response.code() != 201)
                                                {
                                                    val responseBody = response.body()
                                                    val headers = response.headers()
                                                    //Toast.makeText(this, "Error creating user!", Toast.LENGTH_SHORT).show()
                                                    Log.d("Mytag", "response code 201")
                                                }

                                                else
                                                {
                                                    val errorCode = response.code()
                                                    val errorBody = response.errorBody()?.string()
                                                    Log.d("Mytag", "response code other than 201")
                                                    //Toast.makeText(this, "User created susccesfully!", Toast.LENGTH_SHORT).show()
                                                }

                                            } else {

                                                Log.d("Mytag", "response code other than 200-300")
                                                // Handle API error
                                                //val errorCode = response.code()
                                                //val errorBody = response.errorBody()?.string()
                                                // Handle the error based on the response code and body
                                            }
                                        }

                                        override fun onFailure(call: Call<Response<Void>>, t: Throwable) {
                                            // Handle network failure or other exceptions
                                            Log.e("ApiCall", "API Call Failed", t)
                                            t.printStackTrace()
                                        }
                                    })



                                    /*
                                    if (response.isSuccessful) {
                                        if (response.code() != 201)
                                        {
                                            Toast.makeText(this, "Error creating user!", Toast.LENGTH_SHORT).show()
                                        }

                                        else
                                        {
                                            Toast.makeText(this, "User created susccesfully!", Toast.LENGTH_SHORT).show()
                                        }


                                    }
                                    else {
                                        Toast.makeText(this, "Error creating user!", Toast.LENGTH_SHORT).show()
                                    }
                                    */

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