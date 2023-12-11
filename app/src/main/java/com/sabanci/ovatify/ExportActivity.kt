package com.sabanci.ovatify

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sabanci.ovatify.databinding.ExportFlowBinding
import com.sabanci.ovatify.fragment.ArtistExportFragment
import com.sabanci.ovatify.fragment.GenreExportFragment
import com.sabanci.ovatify.databinding.ExportFlowBinding
class ExportActivity : AppCompatActivity() {

    private lateinit var binding: ExportFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ExportFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener{
            onBackPressed()
        }
        var isArtistButton=true
        var isGenreButton=false
        binding.artistButton.setOnClickListener {
            if(!isArtistButton) {
                isArtistButton=true
                isGenreButton=false
                binding.artistButton.setBackgroundResource(R.drawable.upload_open)
                binding.genreButton.setBackgroundResource(R.drawable.upload_closed)
                replaceFragment(ArtistExportFragment())
            }
        }
        binding.genreButton.setOnClickListener {
            if(!isGenreButton) {
                isArtistButton=false
                isGenreButton=true
                binding.artistButton.setBackgroundResource(R.drawable.upload_closed_reverse)
                binding.genreButton.setBackgroundResource(R.drawable.upload_open_reverse)
                replaceFragment(GenreExportFragment())

            }
        }

    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentmanager= supportFragmentManager
        fragmentmanager.beginTransaction().replace(R.id.fragmentContainerViewUpload,fragment).commit()
    }

    }
}