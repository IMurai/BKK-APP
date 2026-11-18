package data

import com.raihaan.bkksmkn6.BuildConfig
import okhttp3.MediaType
import okhttp3.OkHttp
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// @Query menambahkan parameter untuk endpoint endpoint dan @Path menambahkan parameter value seperti id
interface BkkApi { // daftar endpoint
    @GET("jobs") // request dengan endpoint "jobs"
    suspend fun jobs( // mengambil response data
        // suspend fun dengan cara asyncronus agar tidak menggangu program utama

        // paramater untuk endpoint "jobs"
        @Query("page") page: Int = 1, // nomor halaman
        @Query("per_page") perPage: Int = 10, // jumlah konten perhalaman
        @Query("q") q: String? = null,
        @Query("type") type: String? = null
    ): ListResponse<Lowongan> // menyimpan response ke object Lowongan

    @GET("jobs/{id}")
    suspend fun job(@Path("id") id: Int): DetailResponse<Lowongan>

    @GET("companies")
    suspend fun companies(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("q") q: String? = null,
    ): ListResponse<Perusahaan>

    @GET("companies/{id}")
    suspend fun company(@Path("id") id: Int): DetailResponse<Perusahaan>

    @GET("news")
    suspend fun news(
        @Query("page") Page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("q") q: String? = null,
    ): ListResponse<Berita>

    @GET("news/{id}")
    suspend fun newsDetail(@Path("id") id: Int): DetailResponse<Berita>
}

object ApiClient {
    // kunci api ditambahkan otomatis ke setiap permintaan lewat header
    private val http = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .header("X-API-KEY", BuildConfig.BKK_API_KEY)
                    .build()
            )
        }
        .build()

    val api: BkkApi = Retrofit.Builder()
        .baseUrl("https://bkk.smkn6solo.sch.id/api/v1/")
        .client(http)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BkkApi::class.java)
}