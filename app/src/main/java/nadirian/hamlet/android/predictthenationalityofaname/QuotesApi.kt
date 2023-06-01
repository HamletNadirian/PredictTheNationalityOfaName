package nadirian.hamlet.android.predictthenationalityofaname
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuotesApi {

    @GET("./")
    suspend fun getQuotes(@Query("name") page: String): Response<QuoteList>
}