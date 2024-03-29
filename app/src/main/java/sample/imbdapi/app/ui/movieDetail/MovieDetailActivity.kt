package sample.imbdapi.app.ui.movieDetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_venue_detail.*
import kotlinx.android.synthetic.main.progress_view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import sample.imbdapi.app.R
import sample.imbdapi.app.databinding.ActivityVenueDetailBinding
import sample.imbdapi.app.model.structures.MovieStruct
import sample.imbdapi.app.utilities.Consts
import sample.imbdapi.app.utilities.Coroutines


class MovieDetailActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: MovieDetailViewModelFactory by instance()
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var movieStruct: MovieStruct
    private lateinit var dataBinding: ActivityVenueDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_venue_detail)

        showProgressLayout()

        movieStruct = Gson().fromJson(
            intent.getStringExtra(Consts.BATNMAN_SERIALIZE_KEY),
            MovieStruct::class.java
        )
        viewModel = ViewModelProviders.of(this, factory).get(MovieDetailViewModel::class.java)

        Coroutines.main {
            viewModel.getMovieDetail(movieStruct.id)?.observe(this, Observer { movieDetail ->
                if (movieDetail != null) {
                    movieDetail.movieStruct = movieStruct
                    dataBinding.movieDetailStruct = movieDetail

                    Glide.with(this)
                        .load(movieDetail.movieStruct?.Poster)
                        .thumbnail(Glide.with(this).load(R.drawable.gif_placeholder))
                        .into(img_venue)
                } else {
                    Toast.makeText(this, R.string.fetch_needed, Toast.LENGTH_LONG).show()
                    finish()
                }
                hideProgressLayout()
            })
        }

        back.setOnClickListener { finish() }
    }

    private fun showProgressLayout() {
        layout_loading.visibility = View.VISIBLE
        Glide.with(this)
            .load(R.drawable.gif_placeholder)
            .into(img_loading)
    }

    private fun hideProgressLayout() {
        layout_loading.visibility = View.GONE
    }

}
