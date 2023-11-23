package com.sabanci.ovatify

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.sabanci.ovatify.data.MusicInfo
import com.sabanci.ovatify.databinding.ShowMusicBinding

class ShowMusicActivity:AppCompatActivity() {
    lateinit var binding:ShowMusicBinding
    var ratingBeforeChange:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ShowMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var intent = intent

// Check if the intent has extras (data)
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
            ratingBeforeChange=binding.ratingBar.rating.toInt()
        }
        binding.SaveButton.setOnClickListener {
            //will change the rating when API comes
            binding.EditButton.visibility=View.VISIBLE
            binding.SaveButton.visibility=View.INVISIBLE
            binding.CancelButton.visibility=View.INVISIBLE
            binding.ratingBar.setIsIndicator(true)
            Toast.makeText(this,"Rating changed",Toast.LENGTH_SHORT)

        }
        binding.CancelButton.setOnClickListener {
            cancel()
        }
    }
    private fun cancel(){
        binding.EditButton.visibility=View.VISIBLE
        binding.SaveButton.visibility=View.INVISIBLE
        binding.CancelButton.visibility=View.INVISIBLE
        binding.ratingBar.setIsIndicator(true)
        binding.ratingBar.rating=ratingBeforeChange.toFloat()
        Toast.makeText(this,"Canceled",Toast.LENGTH_SHORT)
    }

}