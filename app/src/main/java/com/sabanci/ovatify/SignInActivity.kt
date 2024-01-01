package com.sabanci.ovatify

import TokenManager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.api.RetrofitClient3
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity() {


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_page)
        //TokenManager.initialize(this)



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)



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


        val googleSignInButton = findViewById<Button>(R.id.button4)
        googleSignInButton.setOnClickListener {

            signInGoogle()

        }
    }

    private fun signInGoogle()
    {
        val signInIntent = googleSignInClient.signInIntent

        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null)
            {
                updateUI(account)
            }
        }

        else{
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful)
            {
                val user = it.result?.user

                user?.getIdToken(false)?.addOnCompleteListener { tokenTask ->
                    if (tokenTask.isSuccessful) {
                        // Here we get the Firebase token
                        val firebaseToken = tokenTask.result?.token

                        // Check if the token is not null
                        if (!firebaseToken.isNullOrEmpty()) {
                            // Update the TokenManager with the new token
                            TokenManager.userToken = firebaseToken



                            val call1 = RetrofitClient3(TokenManager.userToken).apiService.createUser(TokenManager.userToken, mapOf("email" to user?.email.toString()))

                            call1.enqueue(object : Callback<Void> {
                                override fun onResponse(call: Call<Void>, response: Response<Void>) {

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
                                        }

                                        else
                                        {

                                            val errorCode = response.code()
                                            val errorBody = response.errorBody()?.string()
                                            Log.d("Mytag", "response code other than 201")
                                            //AUTH_TOKEN = userToken
                                            //Toast.makeText(this, "User created susccesfully!", Toast.LENGTH_SHORT).show()

                                            Log.d("Mytag", "response code 201")
                                        }

                                    } else {

                                        Log.d("Mytag", "response code other than 200-300")
                                        // Handle API error
                                        //val errorCode = response.code()
                                        //val errorBody = response.errorBody()?.string()
                                        // Handle the error based on the response code and body
                                    }
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    // Handle network failure or other exceptions
                                    Log.e("ApiCall", "API Call Failed", t)
                                    t.printStackTrace()
                                }
                            })



                            Thread.sleep(2000)

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
                        } else {
                            // Handle the error when the token is null
                            Toast.makeText(this, "Error retrieving Firebase token", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle the failure to retrieve the token
                        Toast.makeText(this, "Failed to retrieve Firebase token", Toast.LENGTH_SHORT).show()
                    }
                }




                /*
                Log.d("call details currentUser", user.toString())
                var userToken = user?.getIdToken(false)?.result?.token
                //Log.d("call details", userToken.toString())

                TokenManager.userToken = userToken.toString()

                Log.d("call details", TokenManager.userToken)

                //val retrofitCli = RetrofitClient(userToken.toString())

                userToken = "Bearer $userToken"

                 */

            }

            else
            {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


}