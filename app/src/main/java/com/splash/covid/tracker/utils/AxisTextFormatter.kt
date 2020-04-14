package com.splash.covid.tracker.utils

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class AxisTextFormatter : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {

        var out = ""
        try {
            val sdf = SimpleDateFormat("dd MMM")
            out = sdf.format(Date(value.toLong()))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return out
    }
}