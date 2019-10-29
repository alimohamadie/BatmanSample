package test.foursquare.app.model.remoteData

import retrofit2.Response
import test.foursquare.app.model.structures.MovieDetailStruct
import test.foursquare.app.model.structures.responses.MovieRespons

class Requests(private val api: ApiInterface) {

    suspend fun fetchMovie(serialName: String): Response<MovieRespons> {

        return api.searchMovie(
            serialName
        )

    }

    suspend fun fetchMovieDetail(id: String): Response<MovieDetailStruct> {
        return api.getSerialDetial(
            id
        )

    }
}