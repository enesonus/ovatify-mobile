package com.sabanci.ovatify

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.ExportSongDetails
import com.sabanci.ovatify.databinding.ExportFlowBinding
import com.sabanci.ovatify.fragment.ArtistExportFragment
import com.sabanci.ovatify.fragment.GenreExportFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import android.Manifest



class ExportActivity : AppCompatActivity() {

    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }

    private lateinit var binding: ExportFlowBinding
    private lateinit var artistList : ArrayList<String>
    private lateinit var genreList : ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ExportFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getAllArtists()
        getAllGenres()
        exportByArtist("Vega")
        exportByGenre("Rap")


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
        val fileName = "data.json"
        try {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
            }

            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
            file.writeText(jsonString)

            Log.d("export by genre", "export by genre successful, file written")

            /*
            val outputStream: FileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            outputStream.write(jsonString.toByteArray())
            outputStream.close()

             */

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}