package sample.imbdapi.app.model

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sample.imbdapi.app.model.db.FilmDatabse
import sample.imbdapi.app.model.preferences.SharedPrefProvider
import sample.imbdapi.app.model.remoteData.Requests
import sample.imbdapi.app.model.remoteData.SafeApiRequest
import sample.imbdapi.app.model.structures.MovieDetailStruct
import sample.imbdapi.app.model.structures.MovieStruct
import sample.imbdapi.app.utilities.ConnectionUtil
import sample.imbdapi.app.utilities.Coroutines
import sample.imbdapi.app.utilities.GlobalActivity
import java.io.IOException
import java.lang.NullPointerException

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
        if (isFetchNeeded())
            withContext(Dispatchers.IO) {
                try {
                    val moviesRespons = requests.fetchMovie(
                        serialName
                    ).body()
                    movieEntityList.postValue(moviesRespons?.list)
                } catch (e: IOException) {
                }
            }
    }

    suspend fun fetchMovieDetail(id: String): LiveData<MovieDetailStruct>? {
        return withContext(Dispatchers.IO) {
            try {
                if (ConnectionUtil.isOnline(GlobalActivity.applicationContext())) {
                    val detailRespons = requests.fetchMovieDetail(
                        id
                    ).body()
                    movieDetailList.postValue(detailRespons)
                }
            } catch (e: IOException) {
            }

            getMovieDetailList(id)
        }


    }

    private fun isFetchNeeded(): Boolean {
        return true
    }

    //    db

    private fun saveMovie(movieEntityList: ArrayList<MovieStruct>?) {
        Coroutines.io {
            try {
                db.getMovieDao().saveAllMovies(movieEntityList!!)
            }catch (e:NullPointerException){}
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