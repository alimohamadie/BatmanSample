package sample.imbdapi.app.model.structures

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details")
data class MovieDetailStruct(
    @PrimaryKey(autoGenerate = false)
    var imdbID: String,
    var Rated: String?,
    var Released: String?,
    var Runtime: String?,
    var Genre: String?,
    var Director: String?,
    var Writer: String,
    var Actors: String,
    var Plot: String,
    var Language: String,
    var Country: String,
    var Awards: String,
    var imdbRating: String,
    var created_att: Long = System.currentTimeMillis(),
    @Embedded
    var movieStruct: MovieStruct?
)