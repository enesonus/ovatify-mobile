package com.sabanci.ovatify.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.UploadActivity
import com.sabanci.ovatify.adapter.LibraryAdapter
import com.sabanci.ovatify.adapter.LibraryAdapter2
import com.sabanci.ovatify.utils.SampleData

class LibraryFragment:Fragment(R.layout.music_list) {
    private lateinit var adapter: LibraryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Any> //data is not initialized yet
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val rootView = inflater.inflate(R.layout.music_list, container, false)


        val myButton = view.findViewById<Button>(R.id.button2)
        myButton.setOnClickListener {
            val intent = Intent(requireActivity(), UploadActivity::class.java)
            startActivity(intent)

        }

        recyclerView = view.findViewById(R.id.library_recycler_view)
        recyclerView.adapter=LibraryAdapter2(SampleData.collections)

        //return rootView
    }
}