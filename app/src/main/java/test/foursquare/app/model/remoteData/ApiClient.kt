package test.foursquare.app.model.remoteData

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import test.foursquare.app.utilities.Consts
import java.util.concurrent.TimeUnit

class ApiClient {

    private fun checkStatusCode(code: Int): Boolean {
        when (code) {
            400 -> {
//                todo show bad request message
                return false
            }
        }
        return true
    }

    fun getClinet(): ApiInterface {
        val okHttpClient = OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(NetworkConnectionInterceptor())
            .addInterceptor { chain ->
                var request: Request = chain.request()

                val authUrl = request.url().newBuilder()
                    .addQueryParameter(
                        Consts.API_KEY_KEY,
                        Consts.API_KEY_VALUE
                    )
                    .build()

                request = request.newBuilder()
                    .url(authUrl)
                    .build()

                val response = chain.proceed(request)

                checkStatusCode(response.code())

                response
            }.build()


        return Retrofit.Builder()
            .baseUrl(Consts.BASE_URL_VALUE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiInterface::class.java)
    }

}