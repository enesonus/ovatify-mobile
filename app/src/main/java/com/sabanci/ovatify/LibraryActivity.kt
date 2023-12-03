package com.sabanci.ovatify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sabanci.ovatify.adapter.LibraryAdapter2
import com.sabanci.ovatify.databinding.MusicListBinding
import com.sabanci.ovatify.utils.SampleData

class LibraryActivity : AppCompatActivity() {

    private lateinit var binding: MusicListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MusicListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*
        binding.apply {
            libraryRecyclerView.adapter = LibraryAdapter2(SampleData.collections)
        }

         */




    }
}