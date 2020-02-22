package com.abdelaty.marvelheroes.mainscreen.main

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdelaty.marvelheroes.R
import com.abdelaty.marvelheroes.data.network.response.Result
import com.abdelaty.marvelheroes.inflate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.character_rv_item.view.*

var context: Context? = null

class RecyclerAdapter(
    private val heroesList: ArrayList<Result>,
    private val requireContext: Context,
    private val itemClickListener: OnItemClickListener

) : RecyclerView.Adapter<RecyclerAdapter.HeroesImagesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesImagesHolder {
        val inflatedView = parent.inflate(R.layout.character_rv_item, false)
        context = requireContext
        return HeroesImagesHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: HeroesImagesHolder, position: Int) {

        var itemHero = heroesList[position]
        holder.bindView(itemHero, itemClickListener)
    }

    override fun getItemCount() = heroesList.size
    class HeroesImagesHolder(v: View) :
        RecyclerView.ViewHolder(v), View.OnClickListener {


        private var view: View = v
        private var heroesResponse: Result? = null
        fun bindView(marvelApiResponse: Result, clickListener: OnItemClickListener) {
            this.heroesResponse = marvelApiResponse
            Glide.with(view.context)
                .load(heroesResponse!!.thumbnail.path + "/detail.jpg")
                .into(view.hero_img_iv)
            view.hero_name_tv.text = heroesResponse!!.name
            itemView.setOnClickListener {
                clickListener.onItemClicked(marvelApiResponse)
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
    fun onItemClicked(result: Result)
}