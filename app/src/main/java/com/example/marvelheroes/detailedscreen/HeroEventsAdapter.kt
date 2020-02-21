package com.example.marvelheroes.detailedscreen


import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelheroes.R
import com.example.marvelheroes.data.network.response.Result
import com.example.marvelheroes.inflate
import kotlinx.android.synthetic.main.comics_rv_item.view.*

class HeroEventsAdapter(
    private val eventsList: List<Result>,
    private val requireContext: Context,
    private val itemClickListener: OnItemClickListener

) : RecyclerView.Adapter<HeroEventsAdapter.HeroEventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroEventsViewHolder {
        val inflatedView = parent.inflate(R.layout.comics_rv_item, false)
        context = requireContext
        return HeroEventsViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: HeroEventsViewHolder, position: Int) {

        val eventsItem = eventsList[position]
        holder.bindView(eventsItem, itemClickListener)
    }

    override fun getItemCount() = eventsList.size
    class HeroEventsViewHolder(v: View) :
        RecyclerView.ViewHolder(v), View.OnClickListener {


        private var view: View = v
        private var eventsResponse: Result? = null
        fun bindView(eventsApiItemResponse: Result, clickListener: OnItemClickListener) {
            this.eventsResponse = eventsApiItemResponse
            Glide.with(view.context)
                .load(eventsResponse!!.thumbnail.path + "/detail.jpg")
                .into(view.comics_poster_iv)
            view.comics_title_tv.text = eventsResponse!!.title
            itemView.setOnClickListener {
                clickListener.onItemClicked(eventsApiItemResponse.resourceURI)
            }
        }

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
        }
    }

}
