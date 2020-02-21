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


var context: Context? = null

class HeroDataAdapter(
    private val comicsList: List<Result>,
    private val requireContext: Context,
    private val itemClickListener: OnItemClickListener

) : RecyclerView.Adapter<HeroDataAdapter.ComicsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        val inflatedView = parent.inflate(R.layout.comics_rv_item, false)
        context = requireContext
        return ComicsViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {

        val comicsItem = comicsList[position]
        holder.bindView(comicsItem, itemClickListener)
    }

    override fun getItemCount() = comicsList.size
    class ComicsViewHolder(v: View) :
        RecyclerView.ViewHolder(v), View.OnClickListener {


        private var view: View = v
        private var comicsResponse: Result? = null
        fun bindView(comicsApiItemResponse: Result, clickListener: OnItemClickListener) {
            this.comicsResponse = comicsApiItemResponse
            if (comicsResponse!!.thumbnail != null)
                Glide.with(view.context)
                    .load(comicsResponse!!.thumbnail.path + "/detail.jpg")
                    .into(view.comics_poster_iv)

            view.comics_title_tv.text = comicsResponse!!.title
            itemView.setOnClickListener {
                clickListener.onItemClicked(comicsApiItemResponse.resourceURI)
            }
        }

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
        }
    }

}

interface OnItemClickListener {
    fun onItemClicked(resourceURI: String)
}