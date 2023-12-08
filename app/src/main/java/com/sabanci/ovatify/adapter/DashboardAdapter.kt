package com.sabanci.ovatify.adapter

import android.R.attr.bottom
import android.R.attr.left
import android.R.attr.right
import android.R.attr.top
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import co.yml.charts.common.extensions.isNotNull
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.sabanci.ovatify.R
import com.sabanci.ovatify.data.ChartData
import java.io.File
import java.io.FileOutputStream


class DashboardAdapter(private val datalist:ArrayList<ChartData>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class BarChartViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val barChart=itemView.findViewById<BarChart>(R.id.bar_chart)
        val descrpText=itemView.findViewById<TextView>(R.id.description)
        val shareButton=itemView.findViewById<Button>(R.id.shareButton)

    }
    class LineChartViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val lineChart = itemView.findViewById<LineChart>(R.id.line_chart)
        val descrpText = itemView.findViewById<TextView>(R.id.description2)
        val shareButton = itemView.findViewById<Button>(R.id.shareButton2)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==0){val view = LayoutInflater.from(parent.context).inflate(R.layout.added_songs_charts, parent, false)
            return BarChartViewHolder(view)
        }
        else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_addition_by_count_chart, parent, false)
            return LineChartViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
       return datalist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BarChartViewHolder) {
                val data = datalist[position]
                val description = data.barChartData!!.description
                holder.descrpText.text=description
                holder.barChart.description=null
                val list = data.barChartData.datalist
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
                    val index = value.toInt().coerceIn(0, data.barChartData.NameList.size - 1)
                    return data.barChartData.NameList[index]
                }
            }
            xAxis.valueFormatter = xAxisFormatter
            xAxis.granularity = 1f
            holder.shareButton.setOnClickListener {
                val chartBitmap: Bitmap = viewToBitmap(holder.itemView)
                lateinit var imagePath: String
                if (chartBitmap != null) {
                    val context = holder.itemView.context

                    // Save the bitmap to a file using FileProvider
                    val file = File(context.cacheDir, "chart.png")
                    try {
                        FileOutputStream(file).use { out ->
                            chartBitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                        }
                        imagePath = file.absolutePath

                        // Get the FileProvider authority
                        val authority = "${context.packageName}.fileprovider"

                        // Create a content URI using FileProvider
                        val contentUri = FileProvider.getUriForFile(context, authority, file)

                        // Create a sharing intent
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "image/*"
                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                        // Start the activity using the context from the holder's view
                        context.startActivity(Intent.createChooser(shareIntent, "Share Chart"))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(holder.itemView.context, "Failed to capture chart image", Toast.LENGTH_SHORT).show()
                }
            }
            }
        else if (holder is LineChartViewHolder){
            val data = datalist[position]
            val description = data.lineChartData!!.description
            holder.descrpText.text=description
            holder.lineChart.description=null
            val list = data.lineChartData!!.datalist
            var entries =ArrayList<Entry>()
            for ((index, value) in list.withIndex()) {
                entries.add(Entry(index.toFloat(), value.toFloat()))
            }
            val lineDataSet = LineDataSet(entries, "Number of Songs")
            lineDataSet.valueTextColor = Color.DKGRAY
            lineDataSet.color=Color.BLACK

            val lineData = LineData(lineDataSet)
            holder.lineChart.data=lineData
            val xAxis = holder.lineChart.xAxis
            xAxis.textColor = Color.BLACK

            val leftYAxis = holder.lineChart.axisLeft
            leftYAxis.textColor = Color.BLACK

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(true)
            val xAxisFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    // Ensure that the index is within the bounds of the list
                    val index = value.toInt().coerceIn(0, data.lineChartData!!.DateList.size - 1)
                    return data.lineChartData!!.DateList[index]
                }
            }
            xAxis.valueFormatter = xAxisFormatter
            xAxis.granularity = 1f
            holder.shareButton.setOnClickListener {
                val chartBitmap: Bitmap = viewToBitmap(holder.itemView)

                lateinit var imagePath: String
                if (chartBitmap != null) {
                    val context = holder.itemView.context
                    // Save the bitmap to a file using FileProvider
                    val file = File(context.cacheDir, "chart.png")
                    try {
                        FileOutputStream(file).use { out ->
                            chartBitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                        }
                        imagePath = file.absolutePath

                        // Get the FileProvider authority
                        val authority = "${context.packageName}.fileprovider"

                        // Create a content URI using FileProvider
                        val contentUri = FileProvider.getUriForFile(context, authority, file)

                        // Create a sharing intent
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "image/*"
                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                        // Start the activity using the context from the holder's view
                        context.startActivity(Intent.createChooser(shareIntent, "Share Chart"))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(holder.itemView.context, "Failed to capture chart image", Toast.LENGTH_SHORT).show()
                }
            }
        }

        }

    override fun getItemViewType(position: Int): Int {
        if(datalist[position].barChartData.isNotNull()) {
            return 0
        }
        else {
            return 1
        }
    }
    private fun viewToBitmap(view: View): Bitmap {

        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}