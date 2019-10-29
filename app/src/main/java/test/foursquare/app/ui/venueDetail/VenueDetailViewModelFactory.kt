package test.foursquare.app.ui.venueDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import test.foursquare.app.model.MovieRepository

class VenueDetailViewModelFactory(
    private val movieRepository: MovieRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VenueDetailViewModel(movieRepository) as T
    }
}