
import com.sabanci.ovatify.data.CreateUserRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


//data class CreateUserRequest(val token: String, val email: String)
interface ApiService {

    @POST("/users/create-user/")
    fun createUser(
        @Header("Authorization") userToken : String?,
        @Body email: String?
    ): Call<Response<Void>>

}

