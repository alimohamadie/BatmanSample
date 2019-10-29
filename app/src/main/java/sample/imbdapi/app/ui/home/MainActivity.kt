package sample.imbdapi.app.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.elconfidencial.bubbleshowcase.BubbleShowCase
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import sample.imbdapi.app.R
import sample.imbdapi.app.ui.adapters.VenueAdapter
import sample.imbdapi.app.utilities.AdapterUtils
import sample.imbdapi.app.utilities.ConnectionUtil
import sample.imbdapi.app.utilities.Consts
import sample.imbdapi.app.utilities.Coroutines


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
            Snackbar.make(
                findViewById(android.R.id.content),
                R.string.error_in_internet_connection,
                Snackbar.LENGTH_LONG
            ).show()

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

        viewModel.getMoviedList().observe(this, Observer { movieList ->

            Coroutines.main {

                lazy_load_progressview.visibility = View.VISIBLE
                adapter.restoreItems(movieList, 0)
                lazy_load_progressview.visibility = View.GONE
                if (movieList.isEmpty()) viewModel.fetchMovie("batman")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.home_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)

        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Coroutines.main {
                    viewModel.fetchMovie(newText)
                }
                return true
            }
        })

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_search)?.actionView?.let { showHelpCase(it, getString(R.string.descryption_help_searchview)) }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun showHelpCase(view: View, message: String) {
        BubbleShowCaseBuilder(this)
            .description(message)
            .targetView(view)
            .textColor(ContextCompat.getColor(this, R.color.colorAccent))
            .backgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            .descriptionTextSize(14)
            .showOnce(Consts.HASH_KEY_SEARCH_VIEW_VALUE)
            .arrowPosition(BubbleShowCase.ArrowPosition.TOP)
            .show()
    }

}
