package com.sabanci.ovatify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sabanci.ovatify.databinding.UploadSongBinding
import com.sabanci.ovatify.fragment.ManualSongUploadFragment

class UploadActivity:AppCompatActivity() {
    lateinit private var binding: UploadSongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=UploadSongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var isSongButtonClicked=true
        var isFileButtonClicked=false

        binding.songButton.setOnClickListener{
            if (!isSongButtonClicked) {
                isSongButtonClicked = true
                isFileButtonClicked = false
                binding.songButton.setBackgroundResource(R.drawable.upload_open)
                binding.fileButton.setBackgroundResource(R.drawable.upload_closed)
                replaceFragment(ManualSongUploadFragment())
            }
        }
        binding.fileButton.setOnClickListener{
            if (!isFileButtonClicked) {
                isSongButtonClicked = false
                isFileButtonClicked = true
                binding.songButton.setBackgroundResource(R.drawable.upload_closed_reverse)
                binding.fileButton.setBackgroundResource(R.drawable.upload_open_reverse)
                Log.e("e","file is clicked")
                replaceFragment(FileSongUploadFragment())
                Log.e("e","fragment replaced")
            }
        }
        binding.backButton.setOnClickListener{
            var newIntent = Intent(this, HomePageActivity::class.java)
            startActivity(newIntent)
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentmanager= supportFragmentManager
        fragmentmanager.beginTransaction().replace(R.id.fragmentContainerViewUpload,fragment).commit()
    }
}