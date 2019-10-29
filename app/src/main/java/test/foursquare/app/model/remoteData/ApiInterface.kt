package test.foursquare.app.model.remoteData

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import test.foursquare.app.model.structures.MovieDetailStruct
import test.foursquare.app.model.structures.responses.MovieRespons


interface ApiInterface {
    companion object {
        operator fun invoke(apiClient: ApiClient): ApiInterface {
            return apiClient.getClinet()
        }
    }

    @GET(".")
    suspend fun searchMovie(
        @Query("s") serialName: String

    ): Response<MovieRespons>

    @GET(".")
    suspend fun getSerialDetial(
        @Query("i") serialId: String
    ): Response<MovieDetailStruct>
}