package com.example.marvelheroes.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelheroes.R
import com.example.marvelheroes.data.network.MarvelApiService
import com.example.marvelheroes.data.network.response.Result
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private lateinit var viewModel: MainViewModel
private lateinit var linearLayoutManager: LinearLayoutManager
private lateinit var result: ArrayList<Result>

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val apiService = MarvelApiService()
        GlobalScope.launch(Dispatchers.Main) {
            val marvelResponse = apiService.getCharactersRespnoseAsync().await()
            message.text = marvelResponse.data.results.get(0).name
            result = marvelResponse.data.results as ArrayList<Result>
            linearLayoutManager = LinearLayoutManager(activity)
            characters_rv.layoutManager = linearLayoutManager
            var adapter = RecyclerAdapter(result)
            characters_rv.adapter = adapter
        }
        return inflater.inflate(R.layout.main_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel


    }

}
