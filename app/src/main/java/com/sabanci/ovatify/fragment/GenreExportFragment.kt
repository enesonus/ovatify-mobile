package com.sabanci.ovatify.fragment

import CustomPopupMenu
import OnPopupMenuDismissListener
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.sabanci.ovatify.R
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.ExportSongDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class GenreExportFragment: Fragment(R.layout.genre_export_fragment),OnPopupMenuDismissListener {
    lateinit var  genreList:ArrayList<String>
    var isdatacome=false
    var selectedGenre=""
    lateinit var selectButton:RelativeLayout
    lateinit var selectText: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectButton=view.findViewById<RelativeLayout>(R.id.relativeLayout)
        val exportButton=view.findViewById<RelativeLayout>(R.id.export_genre_button)
        val customPopupMenu = CustomPopupMenu(requireContext(), selectButton)
        selectText=view.findViewById(R.id.genrechoosebutton)
        customPopupMenu.setOnDismissListener(this) // Setting the activity as the listener

        selectButton.setOnClickListener {
            // Call your API and retrieve data
            getAllGenres()
            if(isdatacome) {
                // Show the popup menu with data
                customPopupMenu.showPopupMenu(genreList)
            }
        }
        exportButton.setOnClickListener {
            if(selectedGenre==""){
                Toast.makeText(requireContext(),"Nothing is selected", Toast.LENGTH_SHORT).show()}
            else{exportByGenre(selectedGenre)}
        }
    }
    override fun onDismiss(selectedItem: String?) {
        if (selectedItem != null) {
            selectedGenre=selectedItem
            selectText.text=selectedGenre
        }
    }
    private fun getAllGenres()
    {
        val callGetAllGenres = RetrofitClient.apiService.getAllGenres()

        callGetAllGenres.enqueue(object : Callback<Map<String, ArrayList<String>>>
        {
            override fun onResponse(
                call: Call<Map<String, ArrayList<String>>>,
                response: Response<Map<String, ArrayList<String>>>
            ) {
                if (response.isSuccessful)
                {
                    Log.d("call genres", "call genres successful")


                    val allGenres = response.body()

                    genreList = arrayListOf()

                    if (allGenres != null) {

                        for ((result, genreName) in allGenres)
                        {
                            genreList = genreName
                        }
                        isdatacome=true

                        Log.d("all genres", genreList.toString())

                    }



                }

                else
                {
                    Log.d("call genres", "call genres unsuccessful")
                }
            }

            override fun onFailure(call: Call<Map<String, ArrayList<String>>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun exportByGenre(genreName : String)
    {
        val callExportByGenre = RetrofitClient.apiService.exportByGenre(genreName)

        callExportByGenre.enqueue(object : Callback<ArrayList<ExportSongDetails>>
        {
            override fun onResponse(
                call: Call<ArrayList<ExportSongDetails>>,
                response: Response<ArrayList<ExportSongDetails>>
            ) {
                if (response.isSuccessful)
                {
                    Log.d("export by genre", "export by genre successful")


                    val songs = response.body() ?:return
                    writeToFile(Gson().toJson(songs))
                    //Log.d("export by genre", "export by genre successful, file downloaded")

                }

                else
                {
                    Log.d("export by genre", "export by genre unsuccessful")
                }
            }

            override fun onFailure(call: Call<ArrayList<ExportSongDetails>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun writeToFile(jsonString: String) {
        val fileName = "${selectedGenre}.json"

        try {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request permission if not granted
                ActivityCompat.requestPermissions(
                    requireContext() as Activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    GenreExportFragment.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                )
            }

            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName
            )

            // Create parent directories if they don't exist
            file.parentFile?.mkdirs()

            file.writeText(jsonString)

            Log.d("export by genre", "Export by genre successful, file written to: ${file.absolutePath}")
            Toast.makeText(requireContext(),"Exported to ${file.absolutePath}",Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }
}