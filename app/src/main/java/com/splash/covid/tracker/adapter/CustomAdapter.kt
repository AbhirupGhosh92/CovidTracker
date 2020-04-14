package com.splash.covid.tracker.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.splash.covid.tracker.fragment.AboutUsFragment
import com.splash.covid.tracker.fragment.ChartsFragment
import com.splash.covid.tracker.fragment.DataFragment
import com.splash.covid.tracker.fragment.HelpfulLinksFragment

class CustomAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager,
    FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val nums = arrayListOf("Real Time Data","Total Graph","Daily Graph","Helpful Links","About Us")


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
           3 -> {
               fragment = HelpfulLinksFragment()
           }

           4 -> {
               fragment = AboutUsFragment()
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