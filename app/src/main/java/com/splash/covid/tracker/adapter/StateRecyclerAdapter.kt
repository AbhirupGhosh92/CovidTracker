package com.splash.covid.tracker.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.splash.covid.tracker.R
import com.splash.covid.tracker.repository.models.DistrictModel
import com.splash.covid.tracker.repository.models.StateModel
import com.splash.covid.tracker.viewholders.ItemViewHolder
import com.splash.covid.tracker.viewmodels.RealTimeDataFragmentViewModel

class StateRecyclerAdapter(var stateListe: ArrayList<StateModel> = ArrayList(),var distList : Map<String,List<DistrictModel>>) : RecyclerView.Adapter<ItemViewHolder>() {


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
                if(distList[stateListe[position].state].isNullOrEmpty().not()) {
                    holder.dataBinding.distRecycler.visibility = View.VISIBLE
                    holder.dataBinding.distRecycler.adapter =
                        DistrictRecyclerAdapter(distList[stateListe[position].state]!!)
                }
                else
                    holder.dataBinding.distRecycler.visibility = View.GONE

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