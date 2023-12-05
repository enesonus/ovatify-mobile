package com.sabanci.ovatify

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.SongDetails
import com.sabanci.ovatify.data.SongDetailsReturn
import com.sabanci.ovatify.databinding.ShowMusicNewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowMusicActivity:AppCompatActivity() {

    private lateinit var theSongDetails: SongDetails

    lateinit var binding:ShowMusicNewBinding
    var ratingBeforeChange:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ShowMusicNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var intent = intent

        getMusicInfo("3geS6mQ5vGwUbkOzqSKEIL")

// Check if the intent has extras (data)
        /*
        if (intent.hasExtra("yourObjectKey")) {
            // Retrieve the serializable object using the key
            val receivedObject = intent.getSerializableExtra("yourObjectKey") as MusicInfo
            binding.musicname.text = receivedObject.name
            binding.musicartist.text = "Artist: " + receivedObject.artist
            binding.musicalbum.text = "Album: " + receivedObject.album
            binding.musicyear.text = "Year: " + receivedObject.year.toString()
            binding.musicgenres.text = "Genre: " + receivedObject.genre
            binding.musicInstruments.text = "Instruments: " + receivedObject.instruments
            binding.musicMood.text = "Mood: " + receivedObject.mood
            binding.ratingBar.rating = receivedObject.rating.toFloat()


        }
*/


        //Log.d("Show Music Info Details", "Song Details: ${theSongDetails}")

        /*

        binding.musicname.text = theSongDetails.name
        binding.musicartist.text = "Artist: " + theSongDetails.artists[0]
        binding.musicalbum.text = "Album: " + theSongDetails.albums[0]
        binding.musicyear.text = "Year: " + theSongDetails.release_year.toString()
        binding.musicgenres.text = "Genre: " + theSongDetails.genres[0] + theSongDetails.genres[1]
        binding.musicInstruments.text = "Instruments: " + theSongDetails.instruments[0]
        binding.musicMood.text = "Mood: " + theSongDetails.mood
        binding.ratingBar.rating = theSongDetails.user_rating.toFloat()


         */




        binding.backButton.setOnClickListener {
            if(binding.EditButton.visibility==View.VISIBLE){
                onBackPressed()
            }
            else {
                cancel()
                onBackPressed()
            }

        }
        binding.EditButton.setOnClickListener {
            binding.EditButton.visibility=View.INVISIBLE
            binding.ratingBar.setIsIndicator(false)
            binding.saveButtonNew.visibility= View.VISIBLE
            binding.cancelButtonNew.visibility=View.VISIBLE
            binding.removeButtonNew.visibility=View.VISIBLE
            ratingBeforeChange=binding.ratingBar.rating.toInt()
        }
        binding.saveButtonNew.setOnClickListener {
            //will change the rating when API comes
            binding.EditButton.visibility=View.VISIBLE
            binding.saveButtonNew.visibility=View.INVISIBLE
            binding.cancelButtonNew.visibility=View.INVISIBLE
            binding.removeButtonNew.visibility=View.INVISIBLE
            binding.ratingBar.setIsIndicator(true)
            Toast.makeText(this,"Rating changed",Toast.LENGTH_SHORT)

        }
        binding.cancelButtonNew.setOnClickListener {
            cancel()
        }
    }
    private fun cancel(){
        binding.EditButton.visibility=View.VISIBLE
        binding.saveButtonNew.visibility=View.INVISIBLE
        binding.cancelButtonNew.visibility=View.INVISIBLE
        binding.removeButtonNew.visibility=View.INVISIBLE
        binding.ratingBar.setIsIndicator(true)
        binding.ratingBar.rating=ratingBeforeChange.toFloat()
        Toast.makeText(this,"Canceled",Toast.LENGTH_SHORT)
    }

    private fun getMusicInfo(songId: String)
    {

        theSongDetails = SongDetails("", "", arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), 1900, 3.0, "", "", "", 1, "", "", "", "")
        Log.d("Show Music Info Details", "Song Details: ${theSongDetails}")
        val callSongDetails = RetrofitClient.apiService.getSongById(songId)
        //val callSongDetails = RetrofitClient.apiService.getSongById("3geS6mQ5vGwUbkOzqSKEIL")



        callSongDetails.enqueue(object : Callback<SongDetailsReturn>{
            override fun onResponse(call: Call<SongDetailsReturn>, response: Response<SongDetailsReturn>) {
                if (response.isSuccessful)
                {

                    Log.d("Call Song Details", "API Call Successful")

                    val songDetailsReturn: SongDetailsReturn? = response.body()

                    Log.d("Show Music Info Details", "Song Details: ${songDetailsReturn}")

                    if (songDetailsReturn != null)
                    {
                        theSongDetails = songDetailsReturn.song_info
                        Log.d("Show Music Info Details", "Song Details: ${theSongDetails}")
                        binding.musicname.text = theSongDetails.name
                        binding.musicartist.text = "Artist: " + theSongDetails.artists[0]
                        binding.musicalbum.text = "Album: " + theSongDetails.albums[0]
                        binding.musicyear.text = "Year: " + theSongDetails.release_year.toString()
                        binding.musicgenres.text = "Genre: " + theSongDetails.genres[0] + theSongDetails.genres[1]
                        //binding.musicInstruments.text = "Instruments: " + theSongDetails.instruments[0]
                        binding.musicMood.text = "Mood: " + theSongDetails.mood
                        binding.ratingBar.rating = theSongDetails.user_rating.toFloat()
                        binding.music2photo.load(theSongDetails.img_url)
                    }

                }

                else
                {
                    Log.e("Call Song Details", "API Call failed")
                }
            }

            override fun onFailure(call: Call<SongDetailsReturn>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })

    }

}