package com.sabanci.ovatify.fragment

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.adapter.LibraryAdapter

class LibraryFragment:Fragment(R.layout.library_fragment) {
    private lateinit var adapter: LibraryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Any> //data is not initialized yet
}