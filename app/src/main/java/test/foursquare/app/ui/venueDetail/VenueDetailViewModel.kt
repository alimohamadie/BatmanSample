package test.foursquare.app.ui.venueDetail

import androidx.lifecycle.ViewModel
import test.foursquare.app.model.MovieRepository

class VenueDetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    suspend fun getMovieDetail(imbdId: String) =
        movieRepository.fetchMovieDetail(
            imbdId
        )
}