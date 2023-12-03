
import com.sabanci.ovatify.data.FavoriteSongsReturn
import com.sabanci.ovatify.data.IdAndRate
import com.sabanci.ovatify.data.RResponse
import com.sabanci.ovatify.data.RecentlyAddedSongsReturn
import com.sabanci.ovatify.data.SpotifySearchReturn
import com.sabanci.ovatify.data.SpotifySongs
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


//data class CreateUserRequest(val token: String, val email: String)
interface ApiService {

    @POST("users/create-user/")
    fun createUser(
        @Header("Authorization") userToken : String?,
        @Body email: String?
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
        @Query("number_of_songs") numberOfSongs: Int = 10
    ): Call<Map<String, Float>> // Adjust the response type as needed
}


