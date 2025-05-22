package com.upc.worktrace

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.tabs.TabLayout

class AdminReportActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart
    private lateinit var chartTabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_report)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()

        setupTabLayout()


        setupCharts()

    }

    private fun initViews() {
        lineChart = findViewById(R.id.lineChart)
        barChart = findViewById(R.id.barChart)
        pieChart = findViewById(R.id.pieChart)
        chartTabLayout = findViewById(R.id.chartTabLayout)
    }

    private fun setupTabLayout() {
        chartTabLayout.addTab(chartTabLayout.newTab().setText("Lineal"))
        chartTabLayout.addTab(chartTabLayout.newTab().setText("Barras"))
        chartTabLayout.addTab(chartTabLayout.newTab().setText("Pastel"))

        chartTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        lineChart.visibility = View.VISIBLE
                        barChart.visibility = View.GONE
                        pieChart.visibility = View.GONE
                    }
                    1 -> {
                        lineChart.visibility = View.GONE
                        barChart.visibility = View.VISIBLE
                        pieChart.visibility = View.GONE
                    }
                    2 -> {
                        lineChart.visibility = View.GONE
                        barChart.visibility = View.GONE
                        pieChart.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupCharts() {
        setupLineChart()
        setupBarChart()
        setupPieChart()
    }

    private fun setupLineChart() {
        lineChart.description.isEnabled = false
        lineChart.setDrawGridBackground(false)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.setPinchZoom(true)

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f

        val meses = arrayOf("Ene", "Feb", "Mar", "Abr", "May", "Jun")
        xAxis.valueFormatter = IndexAxisValueFormatter(meses)

        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 65f))
        entries.add(Entry(1f, 78f))
        entries.add(Entry(2f, 82f))
        entries.add(Entry(3f, 75f))
        entries.add(Entry(4f, 88f))
        entries.add(Entry(5f, 90f))

        val dataSet = LineDataSet(entries, "Productividad")
        dataSet.color = Color.BLUE
        dataSet.setCircleColor(Color.BLUE)
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 4f
        dataSet.setDrawCircleHole(false)
        dataSet.valueTextSize = 10f

        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()
    }

    private fun setupBarChart() {

        barChart.description.isEnabled = false
        barChart.setDrawGridBackground(false)
        barChart.setFitBars(true)

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f


        val empleados = arrayOf("Juan", "Ana", "Carlos", "Laura", "Pedro")
        xAxis.valueFormatter = IndexAxisValueFormatter(empleados)


        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 45000f))
        entries.add(BarEntry(1f, 56000f))
        entries.add(BarEntry(2f, 38000f))
        entries.add(BarEntry(3f, 62000f))
        entries.add(BarEntry(4f, 51000f))


        val dataSet = BarDataSet(entries, "Ventas (€)")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 10f


        val barData = BarData(dataSet)
        barChart.data = barData
        barChart.invalidate()
    }

    private fun setupPieChart() {

        pieChart.description.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = "Asistencia"
        pieChart.setCenterTextSize(16f)
        pieChart.isDrawHoleEnabled = true
        pieChart.legend.isEnabled = true


        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(85f, "Presente"))
        entries.add(PieEntry(10f, "Ausente"))
        entries.add(PieEntry(5f, "Tardanza"))


        val dataSet = PieDataSet(entries, "")
        dataSet.colors = listOf(
            Color.rgb(76, 175, 80),
            Color.rgb(244, 67, 54),
            Color.rgb(255, 193, 7)
        )
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f


        val pieData = PieData(dataSet)
        pieData.setValueTextSize(12f)
        pieData.setValueTextColor(Color.BLACK)
        pieChart.data = pieData
        pieChart.invalidate()
    }

    private fun updateCharts() {
        // Esta función se llamaría cuando el usuario presione el botón de filtrar
        // Aquí actualizarías los gráficos con datos filtrados según las selecciones
        // Por ahora, solo recargamos los gráficos con los mismos datos de ejemplo

        // En una aplicación real, aquí harías peticiones a tu base de datos
        // o API para obtener datos según los filtros seleccionados

        // Luego actualizarías los conjuntos de datos y refrescarías los gráficos
        setupCharts()
    }

}