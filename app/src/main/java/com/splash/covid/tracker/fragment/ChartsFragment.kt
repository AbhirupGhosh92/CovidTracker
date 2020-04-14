package com.splash.covid.tracker.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.splash.covid.tracker.R
import com.splash.covid.tracker.activity.DashboardActivity
import com.splash.covid.tracker.databinding.ChartsFragmentBinding
import com.splash.covid.tracker.utils.AxisTextFormatter
import com.splash.covid.tracker.utils.CustomMarker
import com.splash.covid.tracker.viewmodels.GraphDataFragmentViewModel
import com.splash.covid.tracker.viewmodels.RealTimeDataFragmentViewModel


class ChartsFragment: Fragment(), View.OnClickListener {

    private lateinit var chartsFragmentBinding: ChartsFragmentBinding
    private lateinit var viewModel : GraphDataFragmentViewModel
    private lateinit var sharedViewModel: RealTimeDataFragmentViewModel
    private  var total = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        chartsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.charts_fragment, container, false)
        chartsFragmentBinding.executePendingBindings()
        (context as DashboardActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        viewModel = ViewModelProviders.of(this).get(GraphDataFragmentViewModel::class.java)
        return chartsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        total = arguments?.getBoolean("daily",false)!!
        sharedViewModel = ViewModelProviders.of(requireActivity())[RealTimeDataFragmentViewModel::class.java]
        init()
    }

    private fun init() {
        initViews()
        initListner()

    }

    private fun initViews() {
        viewModel.getGraphDataFromCache(requireContext()).observe(viewLifecycleOwner, Observer {

            viewModel.formatData(it){dataArrayInfected,dataArrayRecovered,dataArrayDead,dataArrayInfectedDaily,dataArrayRecoveredDaily,dataArrayDeadDaily ->

                if(!total) {
                    graphFormatter(chartsFragmentBinding.graphInfected, dataArrayInfected, "inf")
                    graphFormatter(chartsFragmentBinding.graphRecovered, dataArrayRecovered, "rec")
                    graphFormatter(chartsFragmentBinding.graphDeadDaily, dataArrayDead, "ded")
                }
                else
                {
                    graphFormatter(chartsFragmentBinding.graphInfected, dataArrayInfectedDaily, "inf_d")
                    graphFormatter(chartsFragmentBinding.graphRecovered, dataArrayRecoveredDaily, "rec_d")
                    graphFormatter(chartsFragmentBinding.graphDeadDaily, dataArrayDeadDaily, "ded_d")
                }
            }
        })

    }

    @SuppressLint("ResourceType")
    private fun graphFormatter(graph : LineChart, dataArray : Array<Entry?>, type : String)
    {


        when(type)
        {
            "inf" ->
            {

                var dat = LineDataSet(dataArray.toList(), "Total Confirmed")
                dat.setDrawValues(false)
                dat.setDrawFilled(true)
                dat.lineWidth = 3f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dat.fillColor = resources.getColor(R.color.cofirmedColour,null)
                    dat.fillAlpha = resources.getColor(R.color.cofirmedColour,null)
                    dat.circleColors = mutableListOf(resources.getColor(R.color.cofirmedColour,null))
                    dat.color = resources.getColor(R.color.cofirmedColour,null)
                    dat.setDrawCircleHole(false)
                }

                graph.data = LineData(dat)

                graph.axisRight.isEnabled = false
                graph.description.text = "Total Confirmed"



            }

            "rec" ->
            {
                var dat = LineDataSet(dataArray.toList(), "Total Recovered")
                dat.setDrawValues(false)
                dat.setDrawFilled(true)
                dat.lineWidth = 3f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dat.fillColor = resources.getColor(R.color.recoveredColour,null)
                    dat.fillAlpha = resources.getColor(R.color.recoveredColour,null)
                    dat.circleColors = mutableListOf(resources.getColor(R.color.recoveredColour,null))
                    dat.color = resources.getColor(R.color.recoveredColour,null)
                    dat.setDrawCircleHole(false)
                }

                graph.data = LineData(dat)
                graph.description.text = "Total Recovered"
            }

            "ded" ->
            {
                var dat = LineDataSet(dataArray.toList(), "Total Dead")
                dat.setDrawValues(false)
                dat.setDrawFilled(true)
                dat.lineWidth = 3f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dat.fillColor = resources.getColor(R.color.deadColour,null)
                    dat.fillAlpha = resources.getColor(R.color.deadColour,null)
                    dat.circleColors = mutableListOf(resources.getColor(R.color.deadColour,null))
                    dat.color = resources.getColor(R.color.deadColour,null)
                    dat.setDrawCircleHole(false)
                }

                graph.data = LineData(dat)
                graph.description.text = "Total Dead"
            }

            "inf_d" ->
            {
                var dat = LineDataSet(dataArray.toList(), "Daily Confirmed")
                dat.setDrawValues(false)
                dat.setDrawFilled(true)
                dat.lineWidth = 3f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dat.fillColor = resources.getColor(R.color.cofirmedColour,null)
                    dat.fillAlpha = resources.getColor(R.color.cofirmedColour,null)
                    dat.circleColors = mutableListOf(resources.getColor(R.color.cofirmedColour,null))
                    dat.color = resources.getColor(R.color.cofirmedColour,null)
                    dat.setDrawCircleHole(false)
                }

                graph.data = LineData(dat)
                graph.description.text = "Daily Confirmed"

            }

            "rec_d" ->
            {
                var dat = LineDataSet(dataArray.toList(), "Daily Recovered")
                dat.setDrawValues(false)
                dat.setDrawFilled(true)
                dat.lineWidth = 3f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dat.fillColor = resources.getColor(R.color.recoveredColour,null)
                    dat.fillAlpha = resources.getColor(R.color.recoveredColour,null)
                    dat.circleColors = mutableListOf(resources.getColor(R.color.recoveredColour,null))
                    dat.color = resources.getColor(R.color.recoveredColour,null)
                    dat.setDrawCircleHole(false)

                }

                graph.data = LineData(dat)
                graph.description.text = "Daily Recovered"
            }

            "ded_d" ->
            {
                var dat = LineDataSet(dataArray.toList(), "Daily Dead")
                dat.setDrawValues(false)
                dat.setDrawFilled(true)
                dat.lineWidth = 3f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dat.fillColor = resources.getColor(R.color.deadColour,null)
                    dat.fillAlpha = resources.getColor(R.color.deadColour,null)
                    dat.circleColors = mutableListOf(resources.getColor(R.color.deadColour,null))
                    dat.color = resources.getColor(R.color.deadColour,null)
                    dat.setDrawCircleHole(false)
                }

                graph.data = LineData(dat)
                graph.description.text = "Daily Dead"
            }
        }

        graph.marker = CustomMarker(requireContext(),R.layout.marker_layout)

        graph.onChartGestureListener = object : OnChartGestureListener{
            override fun onChartGestureEnd(
                me: MotionEvent?,
                lastPerformedGesture: ChartTouchListener.ChartGesture?
            ) {

            }

            override fun onChartFling(
                me1: MotionEvent?,
                me2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ) {

            }

            override fun onChartSingleTapped(me: MotionEvent?) {

            }

            override fun onChartGestureStart(
                me: MotionEvent?,
                lastPerformedGesture: ChartTouchListener.ChartGesture?
            ) {

            }

            override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {
                sharedViewModel.swipeRefreshLayoutEnabled.invoke(graph.scaleX == 1.0f && graph.scaleY == 1.0f)
            }

            override fun onChartLongPressed(me: MotionEvent?) {

            }

            override fun onChartDoubleTapped(me: MotionEvent?) {
                graph.resetZoom()
                sharedViewModel.swipeRefreshLayoutEnabled.invoke(true)
            }

            override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
                sharedViewModel.swipeRefreshLayoutEnabled.invoke(graph.viewPortHandler.isFullyZoomedOut)
            }

        }





        val xAxis: XAxis = graph.xAxis
        xAxis.labelCount = 6
        xAxis.valueFormatter = AxisTextFormatter()
        graph.setTouchEnabled(true)
        graph.setPinchZoom(true)
        graph.invalidate()
    }



    private fun initListner() {}

    override fun onClick(view: View?) {}
}

