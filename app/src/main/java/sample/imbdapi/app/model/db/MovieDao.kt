package sample.imbdapi.app.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sample.imbdapi.app.model.structures.MovieStruct

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllMovies(venueStruct: List<MovieStruct>?)

    @Query("SELECT * FROM movies ORDER BY created_at Desc")
    fun selectAllMovies(): LiveData<List<MovieStruct>>

}