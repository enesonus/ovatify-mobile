package com.sabanci.ovatify.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.UploadActivity
import com.sabanci.ovatify.adapter.LibraryAdapter

class LibraryFragment:Fragment(R.layout.library_fragment) {
    private lateinit var adapter: LibraryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Any> //data is not initialized yet
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.library_fragment, container, false)
        val myButton = rootView.findViewById<Button>(R.id.button)
        myButton.setOnClickListener {
            val intent = Intent(requireActivity(), UploadActivity::class.java)
            startActivity(intent)

        }
        return rootView
    }
}