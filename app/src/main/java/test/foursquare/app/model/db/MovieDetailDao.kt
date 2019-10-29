package test.foursquare.app.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.foursquare.app.model.structures.MovieDetailStruct

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovieDetails(movieDetailEntity: MovieDetailStruct)

    @Query("SELECT * FROM movie_details WHERE imdbID = :id")
    fun getMovieDetail(id: String): LiveData<MovieDetailStruct>
}