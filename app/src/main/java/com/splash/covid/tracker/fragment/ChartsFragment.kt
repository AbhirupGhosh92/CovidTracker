package com.splash.covid.tracker.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.splash.covid.tracker.R
import com.splash.covid.tracker.activity.DashboardActivity
import com.splash.covid.tracker.databinding.ChartsFragmentBinding
import com.splash.covid.tracker.viewmodels.GraphDataFragmentViewModel
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


class ChartsFragment: Fragment(), View.OnClickListener {

    private lateinit var chartsFragmentBinding: ChartsFragmentBinding
    private lateinit var viewModel : GraphDataFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        chartsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.charts_fragment, container, false)
        chartsFragmentBinding.executePendingBindings()
        (context as DashboardActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        viewModel = ViewModelProviders.of(this).get(GraphDataFragmentViewModel::class.java)
        return chartsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initViews()
        initListner()

    }

    private fun initViews() {
        viewModel.getGraphDataFromCache(requireContext()).observe(viewLifecycleOwner, Observer {

            viewModel.formatData(it){dataArrayInfected,dataArrayRecovered,dataArrayDead,dataArrayInfectedDaily,dataArrayRecoveredDaily,dataArrayDeadDaily ->

                graphFormatter( chartsFragmentBinding.graphInfected,dataArrayInfected,"inf")
                graphFormatter( chartsFragmentBinding.graphRecovered,dataArrayRecovered,"rec")
                graphFormatter( chartsFragmentBinding.graphDead,dataArrayDead,"ded")

                graphFormatter( chartsFragmentBinding.graphInfectedDaily,dataArrayInfectedDaily,"inf_d")
                graphFormatter( chartsFragmentBinding.graphRecoveredDaily,dataArrayRecoveredDaily,"rec_d")
                graphFormatter( chartsFragmentBinding.graphDeadDaily,dataArrayDeadDaily,"ded_d")
            }
        })

    }

    @SuppressLint("ResourceType")
    private fun graphFormatter(graph : GraphView, dataArray : Array<DataPoint?>, type : String)
    {


        when(type)
        {
            "inf" ->
            {
                graph.title = "Total Confirmed";
                val series: LineGraphSeries<DataPoint?> = LineGraphSeries(dataArray)
                series.setAnimated(true)
                series.isDrawDataPoints = true
                series.color = Color.parseColor(resources.getString(R.color.baseColor))
                series.isDrawAsPath = true
                series.title = "Total Confirmed"
                graph.removeAllSeries()
                graph.addSeries(series)

            }

            "rec" ->
            {
                graph.title = "Total Recovered";
                val series: LineGraphSeries<DataPoint?> = LineGraphSeries(dataArray)
                series.setAnimated(true)
                series.isDrawDataPoints = true
                series.isDrawAsPath = true
                series.color = Color.parseColor(resources.getString(R.color.colorPrimaryDark))
                series.title = "Total Recovered"
                graph.removeAllSeries()
                graph.addSeries(series)
            }

            "ded" ->
            {
                graph.title = "Total Dead";
                val series: LineGraphSeries<DataPoint?> = LineGraphSeries(dataArray)
                series.setAnimated(true)
                series.isDrawDataPoints = true
                series.isDrawAsPath = true
                series.title = "Total Dead"
                series.color = Color.parseColor(resources.getString(R.color.colorAccent))
                graph.removeAllSeries()
                graph.addSeries(series)
            }

            "inf_d" ->
            {
                graph.title = "Daily Confirmed";
                val series: LineGraphSeries<DataPoint?> = LineGraphSeries(dataArray)
                series.setAnimated(true)
                series.isDrawDataPoints = true
                series.color = Color.parseColor(resources.getString(R.color.baseColor))
                series.isDrawAsPath = true
                series.title = "Daily Confirmed"
                graph.removeAllSeries()
                graph.addSeries(series)

            }

            "rec_d" ->
            {
                graph.title = "Daily Recovered";
                val series: LineGraphSeries<DataPoint?> = LineGraphSeries(dataArray)
                series.setAnimated(true)
                series.isDrawDataPoints = true
                series.isDrawAsPath = true
                series.color = Color.parseColor(resources.getString(R.color.colorPrimaryDark))
                series.title = "Daily Recovered"
                graph.removeAllSeries()
                graph.addSeries(series)
            }

            "ded_d" ->
            {
                graph.title = "Daily Dead";
                val series: LineGraphSeries<DataPoint?> = LineGraphSeries(dataArray)
                series.setAnimated(true)
                series.isDrawDataPoints = true
                series.isDrawAsPath = true
                series.title = "Daily Dead"
                series.color = Color.parseColor(resources.getString(R.color.colorAccent))
                graph.removeAllSeries()
                graph.addSeries(series)
            }
        }

        // set date label formatter
        graph.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(requireContext());
        graph.gridLabelRenderer.numHorizontalLabels = 3; // only 4 because of the space
        graph.gridLabelRenderer.numVerticalLabels = 3
        graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.HORIZONTAL
        graph.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE
        graph.gridLabelRenderer.isVerticalLabelsVisible = false

// set manual x bounds to have nice steps
        graph.viewport.setMinX(dataArray.first()!!.x - 5.0)
        graph.viewport.setMaxX(dataArray.last()!!.x + 25.0);

        graph.viewport.setMinY(dataArray.first()!!.y-10.0)
        graph.viewport.setMaxY(dataArray.last()!!.y + 50.0)

        graph.viewport.isXAxisBoundsManual = true;
        graph.viewport.isYAxisBoundsManual = true;
        graph.isCursorMode = true

        graph.gridLabelRenderer.setHumanRounding(false);
    }



    private fun initListner() {}

    override fun onClick(view: View?) {}
}

