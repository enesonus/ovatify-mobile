package com.sabanci.ovatify

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.NewSongRating
import com.sabanci.ovatify.data.SongDetails
import com.sabanci.ovatify.data.SongDetailsReturn
import com.sabanci.ovatify.databinding.NewShowMusicBinding
import com.sabanci.ovatify.databinding.ShowMusicNewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowMusicActivity:AppCompatActivity() {

    private lateinit var theSongDetails: SongDetails

    lateinit var binding:NewShowMusicBinding
    var ratingBeforeChange:Int=0

    var newRating : Float = 0.0F
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=NewShowMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var intent = intent

        //getMusicInfo("3geS6mQ5vGwUbkOzqSKEIL")

// Check if the intent has extras (data)

        if (intent.hasExtra("song")) {
            // Retrieve the serializable object using the key
            val receivedObject = intent.getStringExtra("song") as String
            getMusicInfo(receivedObject)

        }



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
            binding.SaveButton.visibility= View.VISIBLE
            binding.CancelButton.visibility=View.VISIBLE
            binding.removeButton.visibility=View.VISIBLE
            ratingBeforeChange=binding.ratingBar.rating.toInt()
            Log.d("New Rating", "Rating when clicked edit button: ${newRating}")
        }
        binding.SaveButton.setOnClickListener {
            //will change the rating when API comes
            binding.EditButton.visibility=View.VISIBLE
            binding.SaveButton.visibility=View.INVISIBLE
            binding.CancelButton.visibility=View.INVISIBLE
            binding.removeButton.visibility=View.INVISIBLE
            binding.ratingBar.setIsIndicator(true)
            Toast.makeText(this,"Rating changed",Toast.LENGTH_SHORT)
            newRating = binding.ratingBar.rating.toFloat()

            if (intent.hasExtra("song")) {
                // Retrieve the serializable object using the key
                val receivedObject = intent.getStringExtra("song") as String
                editRating(receivedObject, newRating)

            }



            Log.d("New Rating", "Rating when clicked edit button: ${newRating}")

        }
        binding.CancelButton.setOnClickListener {
            cancel()
        }

        binding.removeButton.setOnClickListener {
            binding.EditButton.visibility=View.VISIBLE
            binding.SaveButton.visibility=View.INVISIBLE
            binding.CancelButton.visibility=View.INVISIBLE
            binding.removeButton.visibility=View.INVISIBLE
            binding.ratingBar.setIsIndicator(true)
            Toast.makeText(this,"Rating removed",Toast.LENGTH_SHORT)
            Log.d("New Rating", "Rating when clicked edit button: ${newRating}")

            if (intent.hasExtra("song")) {
                // Retrieve the serializable object using the key
                val receivedObject = intent.getStringExtra("song") as String
                deleteRating(receivedObject)

            }

            onBackPressed()


        }
    }
    private fun cancel(){
        binding.EditButton.visibility=View.VISIBLE
        binding.SaveButton.visibility=View.INVISIBLE
        binding.CancelButton.visibility=View.INVISIBLE
        binding.removeButton.visibility=View.INVISIBLE
        binding.ratingBar.setIsIndicator(true)
        binding.ratingBar.rating=ratingBeforeChange.toFloat()
        Toast.makeText(this,"Canceled",Toast.LENGTH_SHORT)
        Log.d("New Rating", "Rating when clicked edit button: ${newRating}")
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
                        if (theSongDetails.name != null)
                        {
                            binding.musicname.text = theSongDetails.name
                        }

                        for (artist in theSongDetails.artists)
                        {
                            binding.musicartist.text = "Artist: " + artist
                            break
                        }
                        //binding.musicartist.text = "Artist: " + theSongDetails.artists[0]

                        for (album in theSongDetails.albums)
                        {
                            binding.musicalbum.text = "Album: " + album
                            break
                        }
                        //binding.musicalbum.text = "Album: " + theSongDetails.albums[0]

                        if (theSongDetails.release_year != null)
                        {
                            binding.musicyear.text = "Year: " + theSongDetails.release_year.toString()
                        }

                        for (genre in theSongDetails.genres)
                        {
                            binding.musicgenres.text = "Genre: " + genre
                            break
                        }
                        //binding.musicgenres.text = "Genre: " + theSongDetails.genres[0]


                        for (musicInstrument in theSongDetails.instruments)
                        {
                            binding.musicInstruments.text = "Instruments: " + musicInstrument
                            break
                        }
                        //binding.musicInstruments.text = "Instruments: " + theSongDetails.instruments[0]

                        if (theSongDetails.mood != null)
                        {
                            binding.musicMood.text = "Mood: " + theSongDetails.mood
                        }
                        //binding.musicMood.text = "Mood: " + theSongDetails.mood

                        if (theSongDetails.user_rating != null)
                        {
                            binding.ratingBar.rating = theSongDetails.user_rating.toFloat()
                            newRating = theSongDetails.user_rating.toFloat()
                            Log.d("New Rating", "Rating when clicked edit button: ${newRating}")
                        }
                        //binding.ratingBar.rating = theSongDetails.user_rating.toFloat()

                        if (theSongDetails.img_url != null)
                        {
                            binding.music2photo.load(theSongDetails.img_url)
                        }
                        //binding.music2photo.load(theSongDetails.img_url)


                        if (theSongDetails.average_rating != null)
                        {
                            binding.averageRating.text = "Average Rating: " + theSongDetails.average_rating
                        }


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

    private fun deleteRating(songId : String)
    {
        val callDeleteRating = RetrofitClient.apiService.deleteSongRating(songId)

        callDeleteRating.enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful) {
                    Toast.makeText(this@ShowMusicActivity, "Rating Deleted Successfully!", Toast.LENGTH_SHORT).show()
                }

                else
                {
                    Log.e("Delete Song Response", response.code().toString())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Delete Song Response", "Delete Song call not successful")
            }

        })
    }

    private fun editRating (songId : String, newRating : Float)
    {
        val newSongRating = NewSongRating(songId, newRating.toString())
        val callEditRating = RetrofitClient.apiService.editSongRating(newSongRating)

        callEditRating.enqueue(object  : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful)
                {
                    Toast.makeText(this@ShowMusicActivity, "Rating Edited Successfully!", Toast.LENGTH_SHORT).show()
                }

                else
                {
                    Log.e("Edit Song Response", response.code().toString())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Edit Song Response", "Edit Song call not successful")
            }

        })
    }

    override fun onRestart() {
        super.onRestart()
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}