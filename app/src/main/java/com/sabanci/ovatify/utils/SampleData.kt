package com.sabanci.ovatify.utils

import com.sabanci.ovatify.model.LibraryModel
import com.sabanci.ovatify.model.MusicModel

object SampleData {

    private val musicModel= arrayListOf<MusicModel>(
        MusicModel(Images.imageUrl0, "Deni", "Denideni")
                /*
        MusicModel(Images.imageUrl1),
        MusicModel(Images.imageUrl2),
        MusicModel(Images.imageUrl3),
        MusicModel(Images.imageUrl4),
        MusicModel(Images.imageUrl5),
        MusicModel(Images.imageUrl6),
        MusicModel(Images.imageUrl7),
        MusicModel(Images.imageUrl8),
        MusicModel(Images.imageUrl9)

                 */
    )

    val collections = mutableListOf(
        LibraryModel("Your Favorites" , musicModel),
        //LibraryModel("Recently Added" , musicModel),
        //LibraryModel("Reccomended for You" , musicModel),
        //LibraryModel("Community Favorites" , musicModel)

    )
}