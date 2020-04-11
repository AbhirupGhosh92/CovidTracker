package com.splash.covid.tracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.splash.covid.tracker.R
import com.splash.covid.tracker.repository.models.DistrictModel
import com.splash.covid.tracker.repository.models.StateModel
import com.splash.covid.tracker.viewholders.ItemViewHolder

class DistrictRecyclerAdapter(var districtList: List<DistrictModel> = ArrayList()): RecyclerView.Adapter<ItemViewHolder>() {

    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        var data = ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_item,parent,false))
        context = parent.context
        return data
    }

    override fun getItemCount(): Int {
        return districtList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {


        if(districtList.isNullOrEmpty())
            holder.dataBinding.llSublist.visibility = View.GONE
        else
            holder.dataBinding.llSublist.visibility = View.VISIBLE

        holder.dataBinding.distText.visibility = View.GONE
        holder.dataBinding.count.visibility = View.GONE

        holder.dataBinding.deathCount.text = districtList[position].confirmed
        holder.dataBinding.areaText.text = districtList[position].name
        holder.dataBinding.recoveredCount.visibility = View.GONE
        holder.dataBinding.activeCount.visibility = View.GONE
        holder.dataBinding.totalCount.visibility = View.GONE

    }
}