package com.splash.covid.tracker.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.splash.covid.tracker.fragment.ChartsFragment
import com.splash.covid.tracker.fragment.DataFragment

class CustomAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager,
    FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val nums = arrayListOf("Real Time Data","Total Graph","Daily Graph")


    override fun getItem(position: Int): Fragment {
        var fragment : Fragment? = null

       when(position) {
          0 -> {
               fragment = DataFragment()
           }
          1 -> {
              fragment = ChartsFragment()
          }
           2-> {
               fragment = ChartsFragment()
           }
       }
        return fragment!!
    }

    override fun getCount(): Int {
        return nums.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return nums[position]
    }
}