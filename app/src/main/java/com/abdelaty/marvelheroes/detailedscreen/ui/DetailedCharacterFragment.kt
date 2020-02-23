package com.abdelaty.marvelheroes.detailedscreen.ui

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdelaty.marvelheroes.R
import com.abdelaty.marvelheroes.data.network.response.Result
import com.abdelaty.marvelheroes.detailedscreen.model.HeroDataAdapter
import com.abdelaty.marvelheroes.detailedscreen.model.OnItemClickListener
import com.abdelaty.marvelheroes.detailedscreen.viewmodel.DetailedCharacterViewModel
import com.abdelaty.marvelheroes.detailedscreen.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.detailed_character_fragment.*
import kotlinx.android.synthetic.main.image_dialog.*


private lateinit var linearLayoutManager: LinearLayoutManager


class DetailedCharacterFragment : Fragment(),
    OnItemClickListener {

    companion object {
        fun newInstance() = DetailedCharacterFragment()
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
        result = arguments?.getSerializable("hero") as Result
        val factory = ViewModelFactory(result.id.toString())
        viewModel = ViewModelProviders.of(this, factory).get(DetailedCharacterViewModel::class.java)
        activity?.actionBar?.hide()

        prepareUi(result)
        getHeroData()
        hero_details_ly.setOnClickListener {
            val uri = Uri.parse(result.urls[0].url)
            val intent: Intent? = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        hero_wiki_ly.setOnClickListener {
            val uri = Uri.parse(result.urls[1].url)
            val intent: Intent? = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        hero_comic_link_ly.setOnClickListener {
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
    }

    override fun onItemClicked(posterUrl: String) {
        showImageDialog(posterUrl)
    }

    private fun showImageDialog(posterUrl: String) {
        val imageDialog = context?.let { Dialog(it) }
        imageDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        imageDialog?.setContentView(R.layout.image_dialog)
        imageDialog?.show()
        displayImage(imageDialog, posterUrl)
    }

    private fun displayImage(dialog: Dialog?, posterUrl: String) {
        Glide.with(this)
            .load(posterUrl)
            .into(dialog?.dialog_image_iv!!)
        Log.e("posterUtl", posterUrl)
    }

    private fun prepareHeroEventsData(result: List<Result>, recyclerView: RecyclerView) {
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        val adapter =
            activity?.applicationContext?.let {
                HeroDataAdapter(
                    result,
                    activity!!,
                    this
                )
            }
        recyclerView.adapter = adapter
    }
}
