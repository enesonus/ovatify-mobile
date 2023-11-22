package com.sabanci.ovatify

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.RResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class FileSongUploadFragment :Fragment(){

    private val PICK_FILE_REQUEST_CODE=123
    lateinit var chooseButton:RelativeLayout
    lateinit var chosenText:TextView
    lateinit var file:File
    private val apiKey="e5e28a48-8080-11ee-b962-0242ac120002"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.file_song_upload_fragment, container, false)
        // Initialize and set up UI components
        Log.d("YourFragment","onCreateView")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseButton=view.findViewById<RelativeLayout>(R.id.choose_a_file_button)
        chosenText=view.findViewById<TextView>(R.id.no_file_chosen)
        chooseButton.setOnClickListener{
            openfilePicker()
        }
        val uploadButton=view.findViewById<RelativeLayout>(R.id.upload_button)
        uploadButton.setOnClickListener{
            val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestBody)
            val apiService = RetrofitClient.apiService
            val call=apiService.uploadFile(filePart)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        // Handle the response in the coroutine scope
                        try {
                            when (response.code()) {
                                201 -> {
                                    // Status Code: 201 Created
                                    val uploadResponse = response.body()
                                    val message ="Upload successful"
                                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                                }
                                400 -> {
                                    // Status Code: 400 Bad Request
                                    // Check for specific error conditions
                                    val errorBody = response.errorBody()?.string()
                                    Toast.makeText(requireContext(), "400 Bad Request", Toast.LENGTH_SHORT).show()
                                }
                                405 -> {
                                    Toast.makeText(requireContext(), "405 Method Not Allowed", Toast.LENGTH_SHORT).show()
                                }
                                500 -> {
                                    // Status Code: 500 Internal Server Error
                                    Toast.makeText(requireContext(), "500 Internal Server Error", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    // Handle other status codes if needed
                                    Toast.makeText(requireContext(), "Unexpected status code: ${response.code()}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: Exception) {
                            // Handle network errors or exceptions
                            Log.e("Retrofit", "Exception: ${e.message}", e)
                            Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("Retrofit", "Exception: ${t.message}", t)
                    Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun openfilePicker(){
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/json"
            startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedFileUri: Uri? = data?.data
            // Perform actions with the selected file URI
            if (selectedFileUri != null) {
                if (selectedFileUri.path!=null) {
                    chosenText.setText(selectedFileUri.path)
                }
                file=uriToFile(requireContext(), selectedFileUri)
            }

        }
    }

    fun uriToFile(context: Context, uri: Uri): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_file")
        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        return file
    }
}