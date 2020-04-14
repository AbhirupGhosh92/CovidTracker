package com.splash.covid.tracker.viewmodels

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.splash.covid.tracker.repository.Repository
import com.splash.covid.tracker.repository.models.GraphItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GraphDataFragmentViewModel : ViewModel() {



    fun getGraphDataFromCache(context: Context) : LiveData<List<GraphItem>>
    {
        var liveData = MutableLiveData<List<GraphItem>>()

        Repository.getGraphFromCache(context).observe(context as LifecycleOwner, Observer {
            if(it != null)
            {

                liveData.value = Gson().fromJson(it.toString(),object : TypeToken<List<GraphItem>>(){}.type)
            }
        })

        return liveData
    }

    fun formatData(graphList : List<GraphItem>, sippet : (dataArrayInfected : Array<Entry?>,
                                                          dataArrayRecovered : Array<Entry?>,
                                                          dataArrayDead : Array<Entry?>,
                                                          dataArrayInfectedDaily : Array<Entry?>,
                                                          dataArrayRecoveredDaily : Array<Entry?>,
                                                          dataArrayDeadDaily : Array<Entry?>
                                                          ) -> Unit)
    {
        var dataListInfected = ArrayList<Entry>()
        var dataListRecovered = ArrayList<Entry>()
        var dataListDead = ArrayList<Entry>()
        var dataListInfectedDaily = ArrayList<Entry>()
        var dataListRecoveredDaily = ArrayList<Entry>()
        var dataListDeadDaily = ArrayList<Entry>()

        graphList.forEach {
            var date = SimpleDateFormat("dd MMMM yyyy").parse("${it.date} ${Calendar.getInstance().get(
                Calendar.YEAR)}")

            dataListInfected.add(Entry(date?.time?.toFloat()!!,it.totalconfirmed?.toFloat()!!))
            dataListRecovered.add(Entry(date?.time?.toFloat()!!,it.totalrecovered?.toFloat()!!))
            dataListDead.add(Entry(date?.time?.toFloat()!!,it.totaldeceased?.toFloat()!!))

            dataListInfectedDaily.add(Entry(date?.time?.toFloat()!!,it.dailyconfirmed?.toFloat()!!))
            dataListRecoveredDaily.add(Entry(date?.time?.toFloat()!!,it.dailyrecovered?.toFloat()!!))
            dataListDeadDaily.add(Entry(date?.time?.toFloat()!!,it.dailydeceased?.toFloat()!!))

        }

        var dataArrayInfected = arrayOfNulls<Entry?>(dataListInfected.size)
        var dataArrayRecovered = arrayOfNulls<Entry?>(dataListRecovered.size)
        var dataArrayDead = arrayOfNulls<Entry?>(dataListDead.size)

        var dataArrayInfectedDaily = arrayOfNulls<Entry?>(dataListInfectedDaily.size)
        var dataArrayRecoveredDaily = arrayOfNulls<Entry?>(dataListRecoveredDaily.size)
        var dataArrayDeadDaily = arrayOfNulls<Entry?>(dataListDeadDaily.size)


        dataListInfected.toArray(dataArrayInfected)
        dataListRecovered.toArray(dataArrayRecovered)
        dataListDead.toArray(dataArrayDead)

        dataListInfectedDaily.toArray(dataArrayInfectedDaily)
        dataListRecoveredDaily.toArray(dataArrayRecoveredDaily)
        dataListDeadDaily.toArray(dataArrayDeadDaily)

        sippet.invoke(dataArrayInfected,dataArrayRecovered,dataArrayDead,dataArrayInfectedDaily,dataArrayRecoveredDaily,dataArrayDeadDaily)

    }



}