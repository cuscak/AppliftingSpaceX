package ua.cuscak.appliftingspacex.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ua.cuscak.appliftingspacex.domain.Rocket

private const val BASE_URL = "https://api.spacexdata.com/v3/"

enum class SpaceApiStatus { LOADING, ERROR, DONE }

private val interceptor = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
    .baseUrl(BASE_URL)
    .build()


enum class LaunchesApiFilter(val value: String) {
    SHOW_UPCOMING("upcoming"),
    SHOW_PAST("past"),
    SHOW_ALL("") }

/**
 * A public interface that exposes the available API methods
 */
interface SpaceApiService {
    /**
     * Returns a Coroutine [Deferred] [List] of [NetworkRocket] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "rockets" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("rockets")
    fun getRocketsAsync(): Deferred<List<NetworkRocket>>

    @GET("rockets/{id}")
    fun getRocketDetailAsync(@Path("id") rocketName: String): Deferred<NetworkRocket>

    @GET("launches/{filter}")
    fun getAllLaunches(@Path("filter") type: String): Deferred<List<NetworkLaunch>>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object SpaceApi {
    val retrofitService: SpaceApiService by lazy {
        retrofit.create(SpaceApiService::class.java)
    }
}