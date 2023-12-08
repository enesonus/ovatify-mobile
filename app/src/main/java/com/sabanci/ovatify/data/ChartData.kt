package com.sabanci.ovatify.data

data class ChartData(val barChartData: BarChartData?,val lineChartData:LineChartData?)
data class BarChartData(val datalist :ArrayList<Float>, val description:String,val NameList:ArrayList<String>)
data class LineChartData(val datalist:ArrayList<Int>,val description: String,val DateList:ArrayList<String>)
