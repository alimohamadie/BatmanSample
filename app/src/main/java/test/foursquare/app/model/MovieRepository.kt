package test.foursquare.app.model

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import test.foursquare.app.model.db.FilmDatabse
import test.foursquare.app.model.preferences.SharedPrefProvider
import test.foursquare.app.model.remoteData.Requests
import test.foursquare.app.model.remoteData.SafeApiRequest
import test.foursquare.app.model.structures.MovieDetailStruct
import test.foursquare.app.model.structures.MovieStruct
import test.foursquare.app.utilities.Consts
import test.foursquare.app.utilities.Coroutines
import java.io.IOException

class MovieRepository(
    private val sharedPrefProvider: SharedPrefProvider,
    private val db: FilmDatabse,
    private val requests: Requests
) : SafeApiRequest() {

    private val movieEntityList = MutableLiveData<ArrayList<MovieStruct>>()
    private val movieDetailList = MutableLiveData<MovieDetailStruct>()

    init {

        movieEntityList.observeForever {
            saveMovie(it)
        }

        movieDetailList.observeForever {
            saveMovieDetail(it)
        }


    }

    //    webservice
    suspend fun fetchMovie(serialName: String) {
//        if (isFetchNeeded(latLng, Consts.VARIANCE_VALUE))
            withContext(Dispatchers.IO) {
                val moviesRespons = requests.fetchMovie(
                    serialName
                ).body()
                movieEntityList.postValue(moviesRespons?.list)
            }
    }

    suspend fun fetchMovieDetail(id: String): LiveData<MovieDetailStruct>? {
        return withContext(Dispatchers.IO) {
            try {
                val detailRespons = requests.fetchMovieDetail(
                    id
                ).body()
                movieDetailList.postValue(detailRespons)
            } catch (e: IOException) {
            }

            getMovieDetailList(id)
        }


    }

    private fun isFetchNeeded(latLng: Location, radius: Float): Boolean {

        if (latLng.distanceTo(sharedPrefProvider.getLastLocation()) > radius) {
            saveLastLocation(latLng)
            return true
        }

        return false
    }

    //    preferences
    private fun saveLastLocation(latLng: Location) {
        Coroutines.io {
            sharedPrefProvider.saveLastLocation(latLng)
        }
    }

    //    db

    private fun saveMovie(movieEntityList: ArrayList<MovieStruct>) {
        Coroutines.io {
            db.getMovieDao().saveAllMovies(movieEntityList)
        }
    }

    private fun saveMovieDetail(movieDetailStruct: MovieDetailStruct) {
        Coroutines.io {
            db.getMovieDetailDao().saveMovieDetails(movieDetailStruct)
        }
    }

    //    getter

    fun getMoviedList() =
        db.getMovieDao().selectAllMovies()

    private fun getMovieDetailList(id: String) =
        db.getMovieDetailDao().getMovieDetail(id)

}