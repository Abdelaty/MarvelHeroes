package com.example.marvelheroes.ui.main

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelheroes.R
import com.example.marvelheroes.data.network.response.Result
import com.example.marvelheroes.inflate
import kotlinx.android.synthetic.main.character_rv_item.view.*

class RecyclerAdapter(private val heroesList: ArrayList<Result>) :
    RecyclerView.Adapter<RecyclerAdapter.HeroesImagesHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeroesImagesHolder {
        val inflatedView = parent.inflate(R.layout.character_rv_item, false)
        return HeroesImagesHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: HeroesImagesHolder, position: Int) {
        val itemHero = heroesList[position]
        holder.bindView(itemHero)
    }

    override fun getItemCount() = heroesList.size
    class HeroesImagesHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var heroesResponse: Result? = null
        fun bindView(marvelApiResponse: Result) {
            this.heroesResponse = marvelApiResponse
            Glide.with(view.context)
                .load(heroesResponse!!.thumbnail.path + "/detail.jpg")
                .into(view.hero_img_iv)
            view.hero_name_tv.text = heroesResponse!!.name
        }

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

    }
}
