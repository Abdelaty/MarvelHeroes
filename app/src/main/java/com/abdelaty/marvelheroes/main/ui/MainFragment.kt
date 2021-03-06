package com.abdelaty.marvelheroes.main.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdelaty.marvelheroes.R
import com.abdelaty.marvelheroes.data.network.response.Result
import com.abdelaty.marvelheroes.detailedscreen.ui.DetailedCharacterFragment
import com.abdelaty.marvelheroes.main.model.OnItemClickListener
import com.abdelaty.marvelheroes.main.model.RecyclerAdapter
import com.abdelaty.marvelheroes.main.viewmodel.MainViewModel
import com.abdelaty.marvelheroes.search.ui.SearchFragment
import com.abdelaty.marvelheroes.utility.inTransaction
import kotlinx.android.synthetic.main.main_fragment.*
import java.io.Serializable

lateinit var viewModel: MainViewModel
private lateinit var linearLayoutManager: LinearLayoutManager

class MainFragment : Fragment(),
    OnItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var result: Serializable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(
            MainViewModel::class.java
        )
        getHeroes()

        search_iv.setOnClickListener {
            try {
                val bundle = Bundle()
                bundle.putSerializable("hero", result)
                val searchFragment =
                    SearchFragment()
                searchFragment.arguments = bundle
                loadFragment(searchFragment)
            } catch (e: Exception) {
                Log.e(javaClass.name, "Didn't get the list yet!")
            }
        }

    }

    private fun getHeroes() {
        viewModel.response.observe(viewLifecycleOwner, Observer { result ->
            prepareRecyclerView(result as ArrayList<Result>)
            this.result = result
        })

    }

    private fun prepareRecyclerView(result: ArrayList<Result>) {
        linearLayoutManager = LinearLayoutManager(activity)
        characters_rv.layoutManager = linearLayoutManager
        characters_rv.itemAnimator = DefaultItemAnimator()
        val adapter = activity?.applicationContext?.let {
            RecyclerAdapter(
                result,
                activity!!,
                this@MainFragment
            )
        }
        characters_rv.adapter = adapter
    }

    override fun onItemClicked(result: Result) {
        val bundle = Bundle()
        bundle.putSerializable("hero", result)
        bundle.putString("id", result.id.toString())

        val detailedCharacterFragment = DetailedCharacterFragment()
        detailedCharacterFragment.arguments = bundle
        loadFragment(detailedCharacterFragment)
    }

    private fun loadFragment(fragment: Fragment) {
        (context as AppCompatActivity).supportFragmentManager.inTransaction {
            add(R.id.nav_host_fragment, fragment).addToBackStack(null)

        }
    }


}
