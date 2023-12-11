package com.sabanci.ovatify

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.sabanci.ovatify.databinding.ExportFlowBinding
class ExportActivity : AppCompatActivity() {

    private lateinit var binding: ExportFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ExportFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}