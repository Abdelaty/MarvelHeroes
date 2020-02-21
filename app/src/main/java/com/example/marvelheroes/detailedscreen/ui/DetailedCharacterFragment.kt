package com.example.marvelheroes.detailedscreen.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.marvelheroes.R
import com.example.marvelheroes.data.network.response.Result
import com.example.marvelheroes.detailedscreen.HeroDataAdapter
import com.example.marvelheroes.detailedscreen.OnItemClickListener
import com.example.marvelheroes.detailedscreen.viewmodel.DetailedCharacterViewModel
import kotlinx.android.synthetic.main.detailed_character_fragment.*

private lateinit var linearLayoutManager: LinearLayoutManager


class DetailedCharacterFragment : Fragment(), OnItemClickListener {

    companion object {
        fun newInstance() =
            DetailedCharacterFragment()
    }

    private lateinit var viewModel: DetailedCharacterViewModel
    private lateinit var viewModel1: DetailedCharacterViewModel
    lateinit var result: Result
    lateinit var heroData: List<Result>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Log.e("Data Show", result.name)
        return inflater.inflate(R.layout.detailed_character_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailedCharacterViewModel::class.java)
        result = arguments?.getSerializable("hero") as Result
        prepareUi(result)
        activity?.actionBar?.hide()
        getHeroData()
    }


    private fun getHeroData() {
        viewModel.response.observe(
            viewLifecycleOwner,
            Observer { comicsData ->
                prepareComicsRecyclerView(comicsData as List<Result>)
            })
        viewModel.eventsResponse.observe(
            viewLifecycleOwner,
            Observer { eventsData ->
                prepareHeroEventsData(eventsData)
            })
        viewModel.seriesResponse.observe(
            viewLifecycleOwner,
            Observer { seriesData ->
                prepareHeroSeriesData(seriesData)
            })
        viewModel.storiesResponse.observe(
            viewLifecycleOwner,
            Observer { storiesData ->
                prepareHeroStoriesData(storiesData)
            })
    }

    private fun prepareUi(result: Result) {
        hero_name.text = result.name
        hero_descripe.text = result.name
        Glide.with(this)
            .load(result.thumbnail.path + "/detail.jpg")
            .into(hero_poster_iv)
    }

    private fun prepareComicsRecyclerView(result: List<Result>) {
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        hero_comics_rv.layoutManager = linearLayoutManager
        hero_comics_rv.itemAnimator = DefaultItemAnimator()
        val adapter = activity?.applicationContext?.let {
            HeroDataAdapter(result, activity!!, this)
        }
        hero_comics_rv.adapter = adapter
        Log.e("Item:", result[0].title)
    }

    override fun onItemClicked(resourceURI: String) {
    }

    private fun prepareHeroEventsData(result: List<Result>) {
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        hero_events_rv.layoutManager = linearLayoutManager
        hero_events_rv.itemAnimator = DefaultItemAnimator()
        val adapter =
            activity?.applicationContext?.let { HeroDataAdapter(result, activity!!, this) }
        hero_events_rv.adapter = adapter
    }

    private fun prepareHeroSeriesData(result: List<Result>) {
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        hero_series_rv.layoutManager = linearLayoutManager
        hero_series_rv.itemAnimator = DefaultItemAnimator()
        val adapter =
            activity?.applicationContext?.let { HeroDataAdapter(result, activity!!, this) }
        hero_series_rv.adapter = adapter
    }

    private fun prepareHeroStoriesData(result: List<Result>) {
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        hero_stories_rv.layoutManager = linearLayoutManager
        hero_stories_rv.itemAnimator = DefaultItemAnimator()
        val adapter =
            activity?.applicationContext?.let { HeroDataAdapter(result, activity!!, this) }
        hero_stories_rv.adapter = adapter
    }
}
