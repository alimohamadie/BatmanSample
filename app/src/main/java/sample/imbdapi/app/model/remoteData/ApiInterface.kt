package sample.imbdapi.app.model.remoteData

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import sample.imbdapi.app.model.structures.MovieDetailStruct
import sample.imbdapi.app.model.structures.responses.MovieRespons


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