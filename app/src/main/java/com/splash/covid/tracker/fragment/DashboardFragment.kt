package com.splash.covid.tracker.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.splash.covid.tracker.R
import com.splash.covid.tracker.activity.DashboardActivity
import com.splash.covid.tracker.adapter.CustomAdapter
import com.splash.covid.tracker.databinding.DashboardFragmentBinding
import com.splash.covid.tracker.viewmodels.RealTimeDataFragmentViewModel
import java.lang.Exception

class DashboardFragment: Fragment(), View.OnClickListener {

    private lateinit var dashboardFragmentBinding: DashboardFragmentBinding
    private lateinit var viewModel : RealTimeDataFragmentViewModel
    private var customAdapter : CustomAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dashboardFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment, container, false)
        (context as DashboardActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return dashboardFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity())[RealTimeDataFragmentViewModel::class.java]

        init()
    }

    private fun init() {

        viewModel.refreshData(requireContext())

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

            dashboardFragmentBinding.vpMain.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {
                    viewModel.swipeRefreshLayoutEnabled.invoke( state == ViewPager.SCROLL_STATE_IDLE )
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {

                }

            })

        }
        else
        {
            customAdapter?.notifyDataSetChanged()
        }
    }

    private fun initViews() {

        dashboardFragmentBinding.srl.setOnRefreshListener {
            viewModel.refreshData(requireContext())

            viewModel.getTotalValuesFromCache(requireContext()).observe(viewLifecycleOwner, Observer {
                dashboardFragmentBinding.srl.isRefreshing = false
            })

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dashboardFragmentBinding.srl.setColorSchemeColors(resources.getColor(R.color.baseColor,null),resources.getColor(R.color.baseColorDark,null))
        }


        viewModel.swipeRefreshLayoutEnabled = {
            dashboardFragmentBinding.srl.isEnabled = it
        }
    }

    private fun initListner() {}

    override fun onClick(view: View?) {}
}

