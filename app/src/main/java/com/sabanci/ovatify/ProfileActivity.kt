package com.sabanci.ovatify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sabanci.ovatify.databinding.ProfilePageBinding
import com.sabanci.ovatify.databinding.UploadSongBinding

class ProfileActivity:AppCompatActivity() {

    private lateinit var binding: ProfilePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener{
            onBackPressed()
        }
        binding.friendsText.setOnClickListener {
            val intent = Intent(this, FriendFlowActivity::class.java)
            startActivity(intent)
        }


    }

}