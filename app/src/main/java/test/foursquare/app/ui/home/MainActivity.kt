package test.foursquare.app.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import test.foursquare.app.R
import test.foursquare.app.ui.adapters.VenueAdapter
import test.foursquare.app.utilities.AdapterUtils
import test.foursquare.app.utilities.ConnectionUtil
import test.foursquare.app.utilities.Coroutines

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private val adapter: VenueAdapter by lazy {
        VenueAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!ConnectionUtil.isOnline(this))
            Toast.makeText(this, R.string.error_in_internet_connection, Toast.LENGTH_LONG).show()

        AdapterUtils.initialRecVertically(
            rec_venues,
            adapter,
            false
        ).addDecorate(
            rec_venues,
            RecyclerView.VERTICAL,
            ContextCompat.getDrawable(this, R.drawable.shape_line)
        ).addEnterAnimation(rec_venues)


        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        Coroutines.main {
            viewModel.fetchMovie("batman")
        }

        viewModel.getMoviedList().observe(this, Observer { movieList ->

            Coroutines.main {
                lazy_load_progressview.visibility = View.VISIBLE
                adapter.restoreItems(movieList, 0)
                lazy_load_progressview.visibility = View.GONE
            }
        })

    }

}
