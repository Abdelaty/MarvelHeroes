package com.abdelaty.marvelheroes.detailedscreen.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdelaty.marvelheroes.R
import com.abdelaty.marvelheroes.data.network.response.Result
import com.abdelaty.marvelheroes.detailedscreen.HeroDataAdapter
import com.abdelaty.marvelheroes.detailedscreen.OnItemClickListener
import com.abdelaty.marvelheroes.detailedscreen.viewmodel.DetailedCharacterViewModel
import com.bumptech.glide.Glide
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.detailed_character_fragment.*


private lateinit var linearLayoutManager: LinearLayoutManager


class DetailedCharacterFragment : Fragment(), OnItemClickListener {

    companion object {
        fun newInstance() =
            DetailedCharacterFragment()
    }

    private lateinit var viewModel: DetailedCharacterViewModel
    lateinit var result: Result
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detailed_character_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailedCharacterViewModel::class.java)
        result = arguments?.getSerializable("hero") as Result
        prepareUi(result)
        activity?.actionBar?.hide()
        getHeroData()
        hero_details_tv.setOnClickListener {
            val uri = Uri.parse(result.urls[0].url)
            val intent: Intent? = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        hero_wiki_tv.setOnClickListener {
            val uri = Uri.parse(result.urls[1].url)
            val intent: Intent? = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        hero_comic_link_tv.setOnClickListener {
            val uri = Uri.parse(result.urls[2].url)
            val intent: Intent? = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }


    private fun getHeroData() {
        viewModel.response.observe(
            viewLifecycleOwner,
            Observer { comicsData ->
                prepareHeroEventsData(comicsData, hero_comics_rv)
            })
        viewModel.eventsResponse.observe(
            viewLifecycleOwner,
            Observer { eventsData ->
                prepareHeroEventsData(eventsData, hero_events_rv)
            })
        viewModel.seriesResponse.observe(
            viewLifecycleOwner,
            Observer { seriesData ->
                prepareHeroEventsData(seriesData, hero_series_rv)
            })
        viewModel.storiesResponse.observe(
            viewLifecycleOwner,
            Observer { storiesData ->
                prepareHeroEventsData(storiesData, hero_stories_rv)
            })
    }

    private fun prepareUi(result: Result) {
        hero_name.text = result.name
        hero_descripe.text = result.name
        Glide.with(this)
            .load(result.thumbnail.path + "/detail.jpg")
            .into(hero_poster_iv)

        Blurry.with(context)
            .radius(10)
            .sampling(8)
            .color(Color.argb(66, 255, 255, 0))
            .async()
            .animate(500)
            .onto(detailed_hero_container_cl)

    }

    override fun onItemClicked(resourceURI: String) {}

    private fun prepareHeroEventsData(result: List<Result>, recyclerView: RecyclerView) {
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        val adapter =
            activity?.applicationContext?.let { HeroDataAdapter(result, activity!!, this) }
        recyclerView.adapter = adapter
    }
}
