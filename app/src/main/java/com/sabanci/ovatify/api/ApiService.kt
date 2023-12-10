
import com.sabanci.ovatify.data.AllFriendsReturn
import com.sabanci.ovatify.data.FavoriteSongsReturn
import com.sabanci.ovatify.data.FriendReturn
import com.sabanci.ovatify.data.Friends
import com.sabanci.ovatify.data.IdAndRate
import com.sabanci.ovatify.data.NewSongRating
import com.sabanci.ovatify.data.RResponse
import com.sabanci.ovatify.data.RecentlyAddedSongsReturn
import com.sabanci.ovatify.data.SongCounts
import com.sabanci.ovatify.data.SongDetails
import com.sabanci.ovatify.data.SongDetailsReturn
import com.sabanci.ovatify.data.SpotifySearchReturn
import com.sabanci.ovatify.data.SpotifySongs
import com.sabanci.ovatify.data.UserInfo
import com.sabanci.ovatify.data.UserRequestBody
import com.sabanci.ovatify.data.UserStats
import okhttp3.MultipartBody
import okhttp3.RequestBody
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


//data class CreateUserRequest(val token: String, val email: String)
interface ApiService {

    @POST("users/create-user/")
    fun createUser(
        @Header("Authorization") userToken : String,
        @Body email: String?
    ): Call<Response<Void>>

    @PUT("users/login/")
    fun login(
        @Header("Authorization") userToken: String
    ): Call<Response<Void>>

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

    @GET("users/get-user-profile/")
    fun getUserProfile():Call<UserInfo>
    @GET("users/get-profile-stats/")
    fun getUsersStat():Call<UserStats>
    @PUT("users/edit-user-preferences/")
    fun editUserPreferences(@Body requestBody: UserRequestBody):Call<Void>
    
}


