package com.abdelaty.marvelheroes.search.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.abdelaty.marvelheroes.data.network.response.Result

class SearchAdapter(
    private var mContext: Context
) : BaseAdapter() {
    internal var inflater: LayoutInflater = LayoutInflater.from(mContext)
    private val arraylist: ArrayList<Result> = ArrayList()
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItem(p0: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemId(p0: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}