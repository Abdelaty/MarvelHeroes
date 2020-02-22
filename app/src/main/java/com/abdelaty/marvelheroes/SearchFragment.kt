package com.abdelaty.marvelheroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abdelaty.marvelheroes.data.network.response.Result
import com.abdelaty.marvelheroes.mainscreen.main.MainViewModel
import kotlinx.android.synthetic.main.search_fragment.*


class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var result: List<Result>
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        getHeroes()
        heroes_sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                heroes_sv.clearFocus()
                heroes_sv.setQuery("", false)
                Toast.makeText(context, "Hello" + query, Toast.LENGTH_LONG).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(context, "Hello" + newText, Toast.LENGTH_LONG).show()
                return false
            }
        })
    }

    private fun getHeroes() {
        com.abdelaty.marvelheroes.mainscreen.main.ui.viewModel.response.observe(
            viewLifecycleOwner,
            Observer { result ->
            })
    }
}
