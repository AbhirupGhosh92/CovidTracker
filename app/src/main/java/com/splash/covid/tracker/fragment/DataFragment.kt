package com.splash.covid.tracker.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.splash.covid.tracker.R
import com.splash.covid.tracker.activity.DashboardActivity
import com.splash.covid.tracker.adapter.StateRecyclerAdapter
import com.splash.covid.tracker.databinding.RealTimeDataFragmentBinding
import com.splash.covid.tracker.repository.models.StateModel
import com.splash.covid.tracker.viewmodels.RealTimeDataFragmentViewModel


class DataFragment: Fragment(), View.OnClickListener {

    private lateinit var realTimeDataFragmentBinding: RealTimeDataFragmentBinding
    private lateinit var viewModel : RealTimeDataFragmentViewModel
    private var stateItemList = ArrayList<StateModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        realTimeDataFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.real_time_data_fragment, container, false)
        realTimeDataFragmentBinding.executePendingBindings()
        viewModel = ViewModelProviders.of(this).get(RealTimeDataFragmentViewModel::class.java)
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

        viewModel.getStateWiseFromCache(requireContext()).observe(viewLifecycleOwner, Observer {

            Log.d("Response",it.toString())

            stateItemList.clear()
            stateItemList.addAll(it)
            realTimeDataFragmentBinding.stateRecycler.adapter?.notifyDataSetChanged()

        })

        viewModel.getTotalValuesFromCache(requireContext()).observe(viewLifecycleOwner, Observer {

            Log.d("Response",it.toString())

            realTimeDataFragmentBinding.topMainCount.totalCount.text = it.confirmed
            realTimeDataFragmentBinding.topMainCount.recovCount.text = it.recovered
            realTimeDataFragmentBinding.topMainCount.deadCount.text = it.deaths
            realTimeDataFragmentBinding.topMainCount.activeCount.text = it.active
        })
    }

    private fun initViews() {

        realTimeDataFragmentBinding.stateRecycler.adapter = StateRecyclerAdapter(stateItemList)
        realTimeDataFragmentBinding.stateRecycler.layoutManager = LinearLayoutManager(requireContext())
        realTimeDataFragmentBinding.stateRecycler.itemAnimator = DefaultItemAnimator()
    }

    private fun initListner() {


    }

    override fun onClick(view: View?) {

        when(view?.id)
        {

        }

    }
}

