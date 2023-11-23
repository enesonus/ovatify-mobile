
import com.sabanci.ovatify.data.IdAndRate
import com.sabanci.ovatify.data.RResponse
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
import retrofit2.http.Query


//data class CreateUserRequest(val token: String, val email: String)
interface ApiService {

    @POST("/users/create-user/")
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
}


