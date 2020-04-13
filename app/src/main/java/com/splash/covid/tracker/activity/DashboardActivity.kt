package com.splash.covid.tracker.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.splash.covid.tracker.R
import com.splash.covid.tracker.databinding.ActivityDashboardBinding
import com.splash.covid.tracker.fragment.DashboardFragment

class DashboardActivity : AppCompatActivity() {

    lateinit var activityDashboardBinding: ActivityDashboardBinding
    var fragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.new_light_base_color,null)
        }

        activityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        init()
    }

    private fun init() {
        fragmentManager = supportFragmentManager
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer, DashboardFragment())?.commit()
    }
}



