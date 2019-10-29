package test.foursquare.app.model.structures.responses

import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import test.foursquare.app.model.structures.MovieStruct

data class MovieRespons(
    @SerializedName("Search")
    val list: ArrayList<MovieStruct>
)