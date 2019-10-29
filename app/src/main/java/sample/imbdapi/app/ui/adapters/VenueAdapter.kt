package sample.imbdapi.app.ui.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_avenue.view.*
import sample.imbdapi.app.R
import sample.imbdapi.app.model.structures.MovieStruct
import sample.imbdapi.app.ui.movieDetail.MovieDetailActivity
import sample.imbdapi.app.utilities.Consts

class VenueAdapter(
    private val activity: Activity
) :
    RecyclerView.Adapter<VenueAdapter.HolderStruct>() {
    private var venueList = ArrayList<MovieStruct>()
    //    notify item remove by position
    fun removeItem(position: Int) {
        venueList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, venueList.size)
    }

    //     notify item added by position
    fun restoreItem(item: MovieStruct, position: Int) {
        venueList.add(position, item)
        notifyItemInserted(position)
    }

    //     notify multi item added by position
    fun restoreItems(items: List<MovieStruct>, position: Int) {
        try {
            venueList.clear()
            notifyDataSetChanged()
            venueList.addAll(items)
            notifyDataSetChanged()
        } catch (e: Exception) {
        }


    }

    fun getListSize() =
        venueList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderStruct {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_avenue, parent, false)
        return HolderStruct(view)
    }

    override fun getItemCount(): Int {
        return venueList.size
    }

    override fun onBindViewHolder(holder: HolderStruct, position: Int) {
        val item = venueList[position]
        holder.bindData(item)
    }

    inner class HolderStruct(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindData(venueStruct: MovieStruct) {
            itemView.txt_title.text = venueStruct.Title
            itemView.txt_address.text = venueStruct.Year
            itemView.txt_category.text = venueStruct.Type
            Glide.with(itemView.context)
                .load(venueStruct.Poster)
                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(Glide.with(itemView.context).load(R.drawable.gif_placeholder))
                .into(itemView.img_place)
            itemView.ly_venue.setOnClickListener {
                startDetailActivity(venueStruct)
            }
        }

        private fun startDetailActivity(movieStruct: MovieStruct) {
            val intent = Intent(activity, MovieDetailActivity::class.java).apply {
                putExtra(Consts.BATNMAN_SERIALIZE_KEY, Gson().toJson(movieStruct))
            }
            activity.startActivity(intent)
        }
    }


}