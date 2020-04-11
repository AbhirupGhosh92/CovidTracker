package com.splash.covid.tracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.splash.covid.tracker.R
import com.splash.covid.tracker.repository.models.DistrictModel
import com.splash.covid.tracker.repository.models.StateModel
import com.splash.covid.tracker.viewholders.ItemViewHolder

class StateRecyclerAdapter(var stateListe: ArrayList<StateModel> = ArrayList()) : RecyclerView.Adapter<ItemViewHolder>() {


    private var switch = false
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        var data = ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_item,parent,false))
        context = parent.context
        return data
    }

    override fun getItemCount(): Int {

           return stateListe.size

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        if(stateListe.isNullOrEmpty().not()) {


            holder.dataBinding.areaText.text = stateListe[position].state
            holder.dataBinding.totalCount.text = stateListe[position].confirmed
            holder.dataBinding.recoveredCount.text = stateListe[position].recovered
            holder.dataBinding.deathCount.text = stateListe[position].deaths
            holder.dataBinding.activeCount.text = stateListe[position].active

            var dist : ArrayList<DistrictModel> = ArrayList()
            if(stateListe[position].district !=null)
                dist.addAll(stateListe[position].district!!)

            holder.dataBinding.distRecycler.adapter = DistrictRecyclerAdapter(dist)
            holder.dataBinding.distRecycler.layoutManager = LinearLayoutManager(context)
            holder.dataBinding.distRecycler.itemAnimator = DefaultItemAnimator()
            holder.dataBinding.distRecycler.adapter?.notifyDataSetChanged()

            if(stateListe[position].visible)
                holder.dataBinding.llSublist.visibility = View.VISIBLE
            else
                holder.dataBinding.llSublist.visibility = View.GONE


            holder.dataBinding.clRoot.setOnClickListener {

                stateListe[position].visible = !stateListe[position].visible
                notifyDataSetChanged()
            }
        }

    }
}