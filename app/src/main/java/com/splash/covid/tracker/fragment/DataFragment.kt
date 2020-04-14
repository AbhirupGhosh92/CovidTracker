package com.splash.covid.tracker.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.splash.covid.tracker.R
import com.splash.covid.tracker.activity.DashboardActivity
import com.splash.covid.tracker.adapter.StateRecyclerAdapter
import com.splash.covid.tracker.databinding.RealTimeDataFragmentBinding
import com.splash.covid.tracker.repository.models.DistrictModel
import com.splash.covid.tracker.repository.models.StateModel
import com.splash.covid.tracker.viewmodels.RealTimeDataFragmentViewModel


class DataFragment: Fragment(), View.OnClickListener {

    private lateinit var realTimeDataFragmentBinding: RealTimeDataFragmentBinding
    private lateinit var viewModel : RealTimeDataFragmentViewModel
    private var stateItemList = ArrayList<StateModel>()
    private var distItemList = HashMap<String,List<DistrictModel>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        realTimeDataFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.real_time_data_fragment, container, false)
        realTimeDataFragmentBinding.executePendingBindings()
        viewModel = ViewModelProviders.of(requireActivity()).get(RealTimeDataFragmentViewModel::class.java)
        (context as DashboardActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return realTimeDataFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initViews()
        initListner()

        viewModel.refreshData(requireContext())

        viewModel.getStateWiseFromCache(requireContext()).observe(viewLifecycleOwner, Observer {stateList ->


            viewModel.getDistDataFromCache(requireContext()).observe(viewLifecycleOwner, Observer {distList ->

                Log.d("Response",stateList.toString())
                stateItemList.clear()
                stateItemList.addAll(stateList.drop(1))
                distItemList.clear()
                distItemList.putAll(distList)
                realTimeDataFragmentBinding.stateRecycler.adapter?.notifyDataSetChanged()

            })
        })

        viewModel.getTotalValuesFromCache(requireContext()).observe(viewLifecycleOwner, Observer {
            Log.d("Response",it.toString())

            realTimeDataFragmentBinding.topMainCount.totalCount.text = it.confirmed
            realTimeDataFragmentBinding.topMainCount.recovCount.text = it.recovered
            realTimeDataFragmentBinding.topMainCount.deadCount.text = it.deaths
            realTimeDataFragmentBinding.topMainCount.activeCount.text = it.active


            if(it.deltaconfirmed.isNullOrEmpty().not())
                realTimeDataFragmentBinding.topMainCount.totalCountDelta.text = "+${it.deltaconfirmed}"

            if(it.deltaconfirmed.isNullOrEmpty().not())
                realTimeDataFragmentBinding.topMainCount.recovCountDelta.text = "+${it.deltarecovered}"

            if(it.deltaconfirmed.isNullOrEmpty().not())
                realTimeDataFragmentBinding.topMainCount.deadCountDelta.text = "+${it.deltadeaths}"

        })
    }

    private fun initViews() {

        realTimeDataFragmentBinding.stateRecycler.adapter = StateRecyclerAdapter(stateItemList,distItemList)
        realTimeDataFragmentBinding.stateRecycler.layoutManager = LinearLayoutManager(requireContext())
        realTimeDataFragmentBinding.stateRecycler.itemAnimator = DefaultItemAnimator()
        realTimeDataFragmentBinding.stateRecycler.isNestedScrollingEnabled = false




        realTimeDataFragmentBinding.stateRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val topRowVerticalPosition =
                    if (recyclerView == null && recyclerView.childCount == 0) 0 else recyclerView.getChildAt(
                        0
                    ).top
                viewModel.swipeRefreshLayoutEnabled.invoke(topRowVerticalPosition >= 0)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun initListner() {


    }

    override fun onClick(view: View?) {

        when(view?.id)
        {

        }

    }
}

