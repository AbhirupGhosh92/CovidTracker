package com.splash.covid.tracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.splash.covid.tracker.R
import com.splash.covid.tracker.activity.DashboardActivity
import com.splash.covid.tracker.adapter.CustomAdapter
import com.splash.covid.tracker.databinding.DashboardFragmentBinding
import com.splash.covid.tracker.viewmodels.RealTimeDataFragmentViewModel

class DashboardFragment: Fragment(), View.OnClickListener {

    private lateinit var dashboardFragmentBinding: DashboardFragmentBinding
    private lateinit var viewModel : RealTimeDataFragmentViewModel
    private var customAdapter : CustomAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dashboardFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment, container, false)
        dashboardFragmentBinding.executePendingBindings()
        (context as DashboardActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return dashboardFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setUpViewPager()
        initViews()
        initListner()

    }

    private fun setUpViewPager() {
        if(customAdapter == null)
        {
            customAdapter = CustomAdapter(fragmentManager!!)
            dashboardFragmentBinding?.vpMain?.adapter = customAdapter
            dashboardFragmentBinding?.tlMain?.setupWithViewPager(dashboardFragmentBinding?.vpMain)
        }
        else
        {
            customAdapter?.notifyDataSetChanged()
        }
    }

    private fun initViews() {}

    private fun initListner() {}

    override fun onClick(view: View?) {}
}

