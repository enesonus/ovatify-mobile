package com.sabanci.ovatify.adapter

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.internal.ViewUtils.dpToPx
import com.sabanci.ovatify.R
import com.sabanci.ovatify.data.BarChartData

class DashboardAdapter(private val datalist:ArrayList<BarChartData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class BarChartViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val barChart=itemView.findViewById<BarChart>(R.id.bar_chart)
        val descrpText=itemView.findViewById<TextView>(R.id.description)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.added_songs_charts, parent, false)
        return BarChartViewHolder(view)
    }

    override fun getItemCount(): Int {
       return datalist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BarChartViewHolder) {
                val data = datalist[position]
                val description = data.description
                holder.descrpText.text=description
                holder.barChart.description=null
                val list = data.datalist
                val entries = list.mapIndexed { index, value ->
                com.github.mikephil.charting.data.BarEntry(index.toFloat(), value)
            }

                val dataSet = BarDataSet(entries,"Number of Songs")
                dataSet.valueTextColor = Color.DKGRAY
                dataSet.color=Color.BLACK

                val barData = BarData(dataSet)
            holder.barChart.data=barData
            val xAxis = holder.barChart.xAxis
            xAxis.textColor = Color.BLACK

            val leftYAxis = holder.barChart.axisLeft
            leftYAxis.textColor = Color.BLACK

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(true)
            val xAxisFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    // Ensure that the index is within the bounds of the list
                    val index = value.toInt().coerceIn(0, data.NameList.size - 1)
                    return data.NameList[index]
                }
            }
            xAxis.valueFormatter = xAxisFormatter
            xAxis.granularity = 1f
        }


    }

}