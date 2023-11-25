package com.sabanci.ovatify

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sabanci.ovatify.api.RetrofitClient
import com.sabanci.ovatify.data.IdAndRate
import com.sabanci.ovatify.data.RResponse
import com.sabanci.ovatify.data.SpotifySearchReturn
import com.sabanci.ovatify.data.SpotifySongs
import com.sabanci.ovatify.databinding.ManualSongUploadFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManualSongUploadFragment: Fragment() {
    lateinit var songList:List<SpotifySongs>
    private var _binding: ManualSongUploadFragmentBinding? = null
    private val handler = Handler(Looper.getMainLooper())
    var selected:SpotifySongs?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ManualSongUploadFragmentBinding.inflate(inflater, container, false)
        // Initialize and set up UI components
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText=view.findViewById<EditText>(R.id.Song_edittext)
        val searchbutton=view.findViewById<RelativeLayout>(R.id.search)
        searchbutton.setOnClickListener {
            Log.e("search","button is clicked")
            if(editText.text.length>2){
                    search(editText.text.toString())

                Log.e("search","search is done")
             }
        }
        editText.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){

            }
            else{
                if(editText.text.toString().length>2){
                    search(editText.text.toString())
                }
            }
        }
        binding.spotify1.setOnClickListener { highlightSelectedRelativeLayout(binding.spotify1)
        selected=songList[0]
        }
        binding.spotify2.setOnClickListener { highlightSelectedRelativeLayout(binding.spotify2)
        selected=songList[1]
        }
        binding.spotify3.setOnClickListener { highlightSelectedRelativeLayout(binding.spotify3)
        selected=songList[2]
        }
        binding.spotify4.setOnClickListener { highlightSelectedRelativeLayout(binding.spotify4)
        selected=songList[3]
        }
        binding.spotify5.setOnClickListener { highlightSelectedRelativeLayout(binding.spotify5)
        selected=songList[4]
        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){
            }

            override fun afterTextChanged(s: Editable?) {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                search(editText.text.toString())
            },500)
        }
        })
        binding.uploadButton.setOnClickListener {
            val rating=binding.ratingBar.rating
            if (selected!=null){
                val idAndRate=IdAndRate(selected!!.spotify_id,binding.ratingBar.rating.toInt())

                val call=RetrofitClient.apiService.sendSong(idAndRate)
                Log.e("call","will call")
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if(response.isSuccessful) {
                            Log.e("response", "done")
                        }
                        else {
                            Log.e("response",response.code().toString())
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.e("error","error")
                    }

                })
            }
            else {Log.e("selection","problem")}

        }

}
    private fun highlightSelectedRelativeLayout(relativeLayout: RelativeLayout) {
        binding.spotify1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_brown))
        binding.spotify2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_brown))
        binding.spotify3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_brown))
        binding.spotify4.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_brown))
        binding.spotify5.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_brown))
        relativeLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_grey))


    }
    private fun search(text:String){
        binding.spotify1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_brown))
        binding.spotify2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_brown))
        binding.spotify3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_brown))
        binding.spotify4.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_brown))
        binding.spotify5.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_brown))
        if(binding.SongEdittext.text.toString().length>2){
        val call = RetrofitClient.apiService.searchForSong(text)
        Log.e("retrofit","retrofit call okay")
        call.enqueue(object : Callback<SpotifySearchReturn> {
            override fun onResponse(call: Call<SpotifySearchReturn>, response: Response<SpotifySearchReturn>) {
                Log.e("response","response comes")
                if (response.isSuccessful) {
                    Log.e("request  ","response is successfull")

                    val spotifySearchReturn: SpotifySearchReturn? = response.body()
                    if (spotifySearchReturn != null) {
                        songList= spotifySearchReturn.results
                        Log.d("song details", songList[0].songImage.toString())
                    }
                    if (songList!=null&&songList.isNotEmpty()) {
                        if (songList[0] != null) {
                            binding.spotify1name.text = songList[0].track_name
                            binding.spotify1artist.text=(songList[0].artist)
                            binding.spotify1album.text=songList[0].album_name+" "+songList[0].release_year
                            //binding.spotify1photo.setImageResource(songList[0].songImage)
                            binding.spotify1.visibility=View.VISIBLE
                        }
                        else { binding.spotify1.visibility=View.INVISIBLE}
                        if (songList[1] != null) {
                            binding.spotify2name.text = songList[1].track_name
                            binding.spotify2artist.text = songList[1].artist
                            binding.spotify2album.text=songList[1].album_name+" "+songList[1].release_year
                            binding.spotify2.visibility=View.VISIBLE
                        }
                        else { binding.spotify2.visibility=View.INVISIBLE}
                        if(songList[2]!=null) {
                            binding.spotify3name.text = songList[2].track_name
                            binding.spotify3artist.text = songList[2].artist
                            binding.spotify3album.text=songList[2].album_name+" "+songList[2].release_year
                            binding.spotify3.visibility=View.VISIBLE
                        }
                        else { binding.spotify3.visibility=View.INVISIBLE}
                        if (songList[3]!=null) {
                            binding.spotify4name.text = songList[3].track_name
                            binding.spotify4artist.text = songList[3].artist
                            binding.spotify4album.text=songList[3].album_name+" "+songList[3].release_year
                            binding.spotify4.visibility=View.VISIBLE
                        }
                        else { binding.spotify4.visibility=View.INVISIBLE}
                        if(songList[4]!=null) {
                            binding.spotify5name.text = songList[4].track_name
                            binding.spotify5artist.text = songList[4].artist
                            binding.spotify5album.text=songList[4].album_name+" "+songList[4].release_year
                            binding.spotify5.visibility=View.VISIBLE
                        }else { binding.spotify5.visibility=View.INVISIBLE}
                    }
                    // Handle the list of songs as needed
                } else {
                    Log.e("request","request is unsuccessful")
                    // Handle unsuccessful response (HTTP status code other than 200)
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SpotifySearchReturn>, t: Throwable) {
                // Handle network errors or unexpected failures
            }
        })
    }
    }
}