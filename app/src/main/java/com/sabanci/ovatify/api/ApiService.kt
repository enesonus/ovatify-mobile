
//import com.sabanci.ovatify.data.AllFriendsReturn
import com.sabanci.ovatify.data.AllFriendsReturn
import com.sabanci.ovatify.data.AllPlaylistsReturn
import com.sabanci.ovatify.data.DatabaseSearchReturn
import com.sabanci.ovatify.data.ExportSongDetails
import com.sabanci.ovatify.data.FavoriteSongsReturn
import com.sabanci.ovatify.data.FriendReturn
//import com.sabanci.ovatify.data.FriendReturn
//import com.sabanci.ovatify.data.Friends
import com.sabanci.ovatify.data.IdAndRate
import com.sabanci.ovatify.data.NewSongRating
import com.sabanci.ovatify.data.PlaylistByIdReturn
import com.sabanci.ovatify.data.RResponse
import com.sabanci.ovatify.data.RecentlyAddedSongsReturn
import com.sabanci.ovatify.data.RecommendationReturn
import com.sabanci.ovatify.data.RecommendationReturnYouMightLike
import com.sabanci.ovatify.data.RecommendationSinceYouLikeReturn
import com.sabanci.ovatify.data.SongCounts
import com.sabanci.ovatify.data.SongDetailsReturn
import com.sabanci.ovatify.data.SpotifySearchReturn
import com.sabanci.ovatify.data.UserInfo
import com.sabanci.ovatify.data.UserRequestBody
import com.sabanci.ovatify.data.UserStats
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @POST("users/create-user/")
    fun createUser(
        @Header("Authorization") userToken : String,
        @Body email:  Map<String,String>
    ): Call<Void>

/*
    @PUT("users/login/")
    fun login(
        @Header("Authorization") userToken: String
    ): Call<Response<Void>>

 */




    @PUT("users/login/")
    fun login(): Call<Response<Void>>



    @Multipart
    @POST("songs/upload-file/")
    fun uploadFile(
        @Part file: MultipartBody.Part
    ): Call<Void>
    @GET("songs/search-spotify/")
    fun searchForSong(
        @Query("search_string") searchString: String
    ): Call<SpotifySearchReturn>

    @POST("songs/add-song/")
    fun sendSong(@Body idandRate:IdAndRate
    ): Call<Void>

    @GET("users/get-favorite-songs/")
    fun getFavoriteSongs(
        @Query("number_of_songs") numberOfSongs: String
    ) :Call<FavoriteSongsReturn>


    @GET("users/get-recently-added-songs/")
    fun getRecentlyAddedSongs(
        @Query("number_of_songs") numberOfSongs: String
    ) :Call<RecentlyAddedSongsReturn>


    @GET("users/get-favorite-{entity}/")
    fun getEntityCount(
        @Path("entity") entity: String,
    ): Call<Map<String, Float>> // Adjust the response type as needed

    @GET("songs/get-song-by-id/")
    fun getSongById(
        @Query("song_id") songId : String
    ):Call<SongDetailsReturn>


    //edit song rating
    @PUT("users/edit-song-rating/")
    fun editSongRating(@Body newSongRating : NewSongRating
    ) : Call<Void>

    //delete song rating
    @DELETE("users/delete-song-rating/")
    fun deleteSongRating(
        @Query("song_id") songId : String
    ) : Call<Void>

    @GET("users/get-all-friends/")
    fun getAllFriends():Call<AllFriendsReturn>



    @POST("/users/remove-friend/")
    fun removeFriend(
        @Query("friend_id") friend_id:String
    ): Call<Void>
    @POST("users/add-friend/")
    fun addFriend(
        @Query("friend_id") friend_id:String
    ):Call<Void>

    @GET("users/get-all-incoming-requests/")
    fun getIncomingRequests():Call<FriendReturn>

    @GET("users/get-user-profile/")
    fun getUserProfile():Call<UserInfo>

    @GET("users/get-profile-stats/")
    fun getUsersStat():Call<UserStats>

    @PUT("users/edit-user-preferences/")
    fun editUserPreferences(@Body requestBody: UserRequestBody):Call<Void>

    @POST("users/accept-friend-request/")
    fun acceptFriendRequest(
        @Body requestBody: Map<String,String>): Call<RResponse>
    @POST("users/reject-friend-request/")
    fun rejectFriendRequest(
        @Body requestBody: Map<String,String>): Call<RResponse>

    @GET("users/get-all-outgoing-requests/")
    fun getOutgoingRequests():Call<FriendReturn>


    @POST("users/send-friend-request/")
    fun sendFriendRequest(@Body requestBody: Map<String,String>): Call<RResponse>

    @GET("users/get-recent-addition-counts/")
    fun getRecentAdditionCounts():Call<SongCounts>

    @GET("users/get-all-recent/")
    fun getNewlyAddedSongs(
        @Query("number_of_songs") numberOfSongs : Int
    ) : Call<RecentlyAddedSongsReturn>

    @GET("users/recommend-you-might-like/")
    fun getRecommendYouMightLike(
        @Query("count") numberOfSongs : Int
    ) : Call<RecommendationReturnYouMightLike>

    @GET("users/recommend-since-you-like/")
    fun getRecommendSinceYouLike(
        @Query("count") numberOfSongs : Int
    ) : Call<RecommendationSinceYouLikeReturn>

    @GET("users/recommend-friend-mix/")
    fun getRecommendFriendMix(
        @Query("count") numberOfSongs : Int
    ) : Call<RecommendationReturnYouMightLike>

    @POST("users/add-song-rating/")
    fun addSongRating(@Body newSongRating: NewSongRating
    ): Call<Void>

    @GET("users/recommend-friend-listen/")
    fun getRecommendFriendListen(
        @Query("count") numberOfSongs: Int
    ) : Call<RecommendationReturn>

    @GET("users/get-library-artist-names/")
    fun getAllArtists() : Call<Map<String, ArrayList<String>>>

    @GET("users/get-library-genre-names/")
    fun getAllGenres() : Call<Map<String, ArrayList<String>>>


    @GET("users/export-by-artist/")
    fun exportByArtist(
        @Query("artist") artistName : String
    ) : Call<ArrayList<ExportSongDetails>>

    @GET("users/export-by-genre/")
    fun exportByGenre(
        @Query("genre") artistName : String
    ) : Call<ArrayList<ExportSongDetails>>

    @GET ("users/get-playlists/")
    fun getPlaylists(@Query("number_of_playlists") numberOfPlaylists: Int
    ): Call<AllPlaylistsReturn>
    @POST ("users/create-empty-playlist/")
    fun createEmptyPlaylist(@Body requestBody: Map<String,String>
    ): Call<RResponse>
    @GET ("users/get-playlist-by-id/")
    fun getPlaylistById(@Query("playlist_id") playlistId: String
    ):Call<PlaylistByIdReturn>
    @PUT ("users/edit-playlist/")
    fun editPlaylist(@Body requestBody: Map<String, String>):
    Call<RResponse>

    @GET ("songs/search-db")
    fun searchDatabase(@Query("search_string") searchString: String
    ):Call<DatabaseSearchReturn>
    @DELETE ("users/remove-song-from-playlist/")
    fun removeSongFromPlaylist(
        @Query("playlist_id") playlistId:String,
        @Query("song_id") songId:String)
    : Call<RResponse>
    @POST ("users/add-song-to-playlist/")
    fun addSongToPlaylist(@Body requestBody: Map<String, String>):Call<RResponse>
}


