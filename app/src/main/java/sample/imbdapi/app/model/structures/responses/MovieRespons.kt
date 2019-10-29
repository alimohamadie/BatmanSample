package sample.imbdapi.app.model.structures.responses

import com.google.gson.annotations.SerializedName
import sample.imbdapi.app.model.structures.MovieStruct

data class MovieRespons(
    @SerializedName("Search")
    val list: ArrayList<MovieStruct>
)