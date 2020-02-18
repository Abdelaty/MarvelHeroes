package com.example.marvelheroes.detailedscreen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.marvelheroes.R
import com.example.marvelheroes.detailedscreen.DetailedCharacterViewModel


class DetailedCharacterFragment : Fragment() {

    companion object {
        fun newInstance() =
            DetailedCharacterFragment()
    }

    private lateinit var viewModel: DetailedCharacterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detailed_character_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailedCharacterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
