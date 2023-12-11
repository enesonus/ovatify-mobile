package com.sabanci.ovatify.fragment

import CustomPopupMenu
import OnPopupMenuDismissListener
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import android.Manifest
import android.app.Activity
import android.view.ContextThemeWrapper
import android.widget.TextView
import android.widget.Toast
import com.sabanci.ovatify.R
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.ExportSongDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ArtistExportFragment: Fragment(R.layout.artist_export_fragment),OnPopupMenuDismissListener {
    lateinit var  artistList:ArrayList<String>
    var isdatacome=false
    var selectedArtist=""
    lateinit var selectButton:RelativeLayout
    lateinit var selectText:TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectButton=view.findViewById<RelativeLayout>(R.id.relativeLayout)
        val exportButton=view.findViewById<RelativeLayout>(R.id.export_artist_button)
        val customPopupMenu = CustomPopupMenu(requireContext(), selectButton)
        selectText=view.findViewById(R.id.artist_choose)
        customPopupMenu.setOnDismissListener(this) // Setting the activity as the listener

        selectButton.setOnClickListener {
            // Call your API and retrieve data
            getAllArtists()
            if(isdatacome) {
                // Show the popup menu with data
                customPopupMenu.showPopupMenu(artistList)
            }
        }
        exportButton.setOnClickListener {
            if(selectedArtist==""){
                Toast.makeText(requireContext(),"Nothing is selected",Toast.LENGTH_SHORT).show()}
            else{exportByArtist(selectedArtist)}
        }
    }

    override fun onDismiss(selectedItem: String?) {
        if (selectedItem != null) {
            selectedArtist=selectedItem
            selectText.text=selectedArtist
        }
    }
    private fun exportByArtist(artistName : String)
    {
        val callExportByArtist = RetrofitClient.apiService.exportByArtist(artistName)

        callExportByArtist.enqueue(object : Callback<ArrayList<ExportSongDetails>>
        {
            override fun onResponse(
                call: Call<ArrayList<ExportSongDetails>>,
                response: Response<ArrayList<ExportSongDetails>>
            ) {
                if (response.isSuccessful)
                {
                    Log.d("export by artist", "export by artist successful")
                    val songs = response.body() ?:return
                    writeToFile(Gson().toJson(songs))
                }

                else
                {
                    Log.d("export by artist", "export by artist unsuccessful")
                }
            }

            override fun onFailure(call: Call<ArrayList<ExportSongDetails>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }
    fun writeToFile(jsonString: String) {
        val fileName = "${selectedArtist}.json"

        try {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request permission if not granted
                ActivityCompat.requestPermissions(requireContext() as Activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
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

    private fun getAllArtists()
    {
        val callGetAllArtists = RetrofitClient.apiService.getAllArtists()

        callGetAllArtists.enqueue(object : Callback<Map<String, ArrayList<String>>>
        {
            override fun onResponse(
                call: Call<Map<String, ArrayList<String>>>,
                response: Response<Map<String, ArrayList<String>>>
            ) {
                if (response.isSuccessful)
                {
                    Log.d("call artist", "call artist successful")

                    val allArtists = response.body()

                    artistList = arrayListOf()

                    if (allArtists != null) {

                        for ((result, artistName) in allArtists)
                        {
                            artistList = artistName
                        }

                        Log.d("all artists", artistList.toString())
                        isdatacome=true


                    }
                }

                else
                {
                    Log.d("call artist", "call artist unsuccessful")
                }
            }

            override fun onFailure(call: Call<Map<String, ArrayList<String>>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}
