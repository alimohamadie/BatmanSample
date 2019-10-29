package sample.imbdapi.app.model.remoteData

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

// This class use for resolve retrofit error when no internet connection found
class NetworkConnectionInterceptor : Interceptor {


    // Throwing our custom exception 'NoConnectivityException'

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}