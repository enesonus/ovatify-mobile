package com.sabanci.ovatify

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class LibraryFragment:Fragment(R.layout.library_fragment) {
    private lateinit var adapter: LibraryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist:ArrayList<Any> //data is not initialized yet
}