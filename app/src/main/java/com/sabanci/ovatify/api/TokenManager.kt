
import android.content.Context
import android.content.SharedPreferences

object TokenManager {


    public var userToken = ""


    /*
    private const val PREFS_NAME = "MyAppPrefs"
    private const val USER_TOKEN_KEY = "USER_TOKEN"
    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getCurrentUserToken(): String {
        return sharedPreferences.getString(USER_TOKEN_KEY, "") ?: ""
    }

    fun updateUserToken(token: String) {
        sharedPreferences.edit().apply {
            putString(USER_TOKEN_KEY, token)
            apply()
        }
    }

     */
}


