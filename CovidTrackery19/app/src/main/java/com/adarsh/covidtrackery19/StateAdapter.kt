package com.adarsh.covidtrackery19

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_individual.view.*

class StateAdapter(val list: List<StatewiseItem>) :BaseAdapter(){
    override fun getView(p0: Int, p1: View?, p2: ViewGroup): View {
    val view = p1 ?: LayoutInflater.from(p2.context).inflate(R.layout.item_individual, p2, false)
        val item = list[p0]
        view.CNFM.text = SpannableDelta("${item.confirmed}\n ↑ ${item.deltaconfirmed ?:0}", "#D32F2F",
            item.confirmed?.length ?: 0)

        view.RCVD.text = SpannableDelta("${item.recovered}\n ↑ ${item.deltarecovered ?: "0"}",
            "#388E3C",
            item.recovered?.length ?: 0)

        view.DCSD.text = SpannableDelta(  "${item.deaths}\n ↑ ${item.deltadeaths ?: "0"}",
            "#FBC02D",
            item.deaths?.length ?: 0)

        view.ACT.text = SpannableDelta("${item.active}\n ↑ ${item.deltaactive ?: "0"}",
            "#1976D2",
            item.confirmed?.length ?: 0)

        view.stateTV.text = item.state


        return view
    }

    override fun getItem(p0: Int): Any = list[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int = list.size

}