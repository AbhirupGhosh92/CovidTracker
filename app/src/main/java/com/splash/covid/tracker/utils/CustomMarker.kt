package com.splash.covid.tracker.utils

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.splash.covid.tracker.R
import java.text.SimpleDateFormat
import java.util.*

class CustomMarker(context: Context,layoutResource: Int) : MarkerView(context, layoutResource) {

         private var tvCount : TextView = findViewById(R.id.tv_count)
        private var tvDate : TextView = findViewById(R.id.tv_date)
    val sdf = SimpleDateFormat("dd MMM")


    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
            val value = entry?.y?.toDouble() ?: 0.0
            var resText = ""

            tvCount.text = "Count: ${entry?.y}"
            tvDate.text = "Date: ${sdf.format(Date(entry?.x?.toLong()!!))}"
            super.refreshContent(entry, highlight)
        }


        override fun getOffsetForDrawingAtPoint(xpos: Float, ypos: Float): MPPointF {

            var xPos = 0.0f
            var yPos = 0.0f
            if(xpos > 450)
              xPos = -width.toFloat()
            else
                xPos= width.toFloat()-200f

            if(ypos > 250)
                yPos =  -height.toFloat()/3-200f
            else
                yPos = height.toFloat()/3+100f

            return MPPointF(xPos,yPos)

        }



}