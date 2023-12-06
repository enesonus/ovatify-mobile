package com.sabanci.ovatify.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sabanci.ovatify.R
import com.sabanci.ovatify.data.IhomeclickListener
import com.sabanci.ovatify.databinding.LibraryFragmentBinding
import com.sabanci.ovatify.databinding.ManualSongUploadFragmentBinding
import com.sabanci.ovatify.databinding.MusicParentItemBinding
import com.sabanci.ovatify.model.LibraryModel

//recview will be added
class LibraryAdapter(private val collection: List<LibraryModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    private val FIRST_ITEM=0
    private val OTHER_ITEMS=1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType)
        {
            FIRST_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.library_fragment, parent, false)
                UploadButtonViewHolder(view)
            }

            else ->
            {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.music_list, parent, false)
                CollectionViewHolder(view)
            }
        }


        //val view = LayoutInflater.from(parent.context).inflate(R.layout.music_parent_item,parent,false)
        //return CollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder)
        {

            is CollectionViewHolder -> {
                holder.binding.apply {
                    val collection = collection[position]
                    favoriteSongs.text = collection.title
                    val songAdapter = SongAdapter(collection.musicModels, object :
                        IhomeclickListener {
                        override fun onItemClick(position: Int) {
                            // Handle item click here
                            Log.d("Item Position", collection.toString())
                            /*
                            val clickedItem = songList[position]
                            val intent = Intent(, ShowMusicActivity::class.java)
                            intent.putExtra("song", songList[position].id)
                            startActivity(intent)

                             */
                            //Toast.makeText( this@VerticalMusicActivity,"Clicked on $clickedItem", Toast.LENGTH_SHORT).show()
                        }
                    })
                    rvSongChild.adapter = songAdapter
                }

            }

            is UploadButtonViewHolder -> {
                holder.binding2.apply {


                }
            }


        }
    }

    override fun getItemCount() =collection.size
    inner class CollectionViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding = MusicParentItemBinding.bind(itemView)
    }

    inner class UploadButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val binding2 = LibraryFragmentBinding.bind(itemView)
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            FIRST_ITEM
        } else {
            OTHER_ITEMS
        }
    }
}




