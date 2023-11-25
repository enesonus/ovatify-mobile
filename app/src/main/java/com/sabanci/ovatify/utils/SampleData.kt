package com.sabanci.ovatify.utils

import com.sabanci.ovatify.model.LibraryModel
import com.sabanci.ovatify.model.MusicModel

object SampleData {

    private val musicModel= listOf(
        MusicModel(Images.imageUrl0),
        MusicModel(Images.imageUrl1),
        MusicModel(Images.imageUrl2),
        MusicModel(Images.imageUrl3),
        MusicModel(Images.imageUrl4),
        MusicModel(Images.imageUrl5),
        MusicModel(Images.imageUrl6),
        MusicModel(Images.imageUrl7),
        MusicModel(Images.imageUrl8),
        MusicModel(Images.imageUrl9)
    )

    val collections = listOf(
        LibraryModel("Your Favorites" , musicModel),
        LibraryModel("Recently Added" , musicModel),
        LibraryModel("Reccomended for You" , musicModel),
        LibraryModel("Community Favorites" , musicModel)

    )
}