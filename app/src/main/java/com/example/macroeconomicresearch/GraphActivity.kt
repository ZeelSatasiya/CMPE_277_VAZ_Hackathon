package com.example.macroeconomicresearch

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isVisible
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.TooltipPositionMode
import com.example.macroeconomicresearch.databinding.GraphActivityBinding
import com.example.macroeconomicresearch.dialog.InputDialog
import com.example.macroeconomicresearch.fragments.GraphViewModel
import com.example.macroeconomicresearch.retrofit.domain.Result
import com.google.android.material.chip.Chip


class GraphActivity : AppCompatActivity() {

    private lateinit var binding : GraphActivityBinding

    private val viewModel : GraphViewModel by viewModels()


    private val app : App
    get() = application as App
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflate(R.layout.activtiy_graph)

        binding.graphToolbarInclude.text.text = app.loadType.label

        val cartesian = AnyChart.line()
                    cartesian.crosshair().enabled(true)
                    cartesian.crosshair()
                        .yLabel(true)

                    cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.title(app.loadType.label)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)
        binding.chart.setChart(cartesian)



        viewModel.macroEconomicUSDLoadUseCase.observe().observe(this) {
            when (it) {
                is Result.Success -> {
                    cartesian.removeAllSeries()


                    val map = it.data.groupBy { it1 -> ((it1.asMap())["country"])!!.asMap()["id"]}


                    map.forEach {it->
                        val set = com.anychart.data.Set.instantiate()

//                        Log.e("MAINAA", it.toString())
                        val entries = it.value.reversed().map { it1 ->
//                        (it1 as LinkedHashTreeMap)["data"]
                            ValueDataEntry(
                                (it1.asMap())["date"].toString(),
                                it1.asMap()["value"]?.toString()?.toDouble()
                            )
                        }
//                        Log.e("ENTRIES", entries.toString())
//
                        set.data(entries)
                        cartesian.addSeries(set)
                    }


                }
                else -> {

                }
            }
        }

        binding.apply.setOnClickListener {
            val fromYear = binding.etFromYear.text.toString()
            val toYear = binding.etToYear.text.toString()
            if(fromYear.isEmpty() || toYear.isEmpty() || fromYear.toInt()>=toYear.toInt() || fromYear.length!=4 || toYear.length!=4)
            {
                Toast.makeText(this, "Enter a valid to and from year", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.e("GRAPH", binding.chipGroup.children.filter{(it as Chip).isChecked }.map { it.tag }.joinToString(";"))

            viewModel.loadData(app.loadType, fromYear, toYear, binding.chipGroup.children.filter{(it as Chip).isChecked }.map { it.tag }.joinToString(";"))
        }


        binding.annotate.isVisible = PreferenceStorage.loginType==LoginType.RESEARCHER
        binding.annotate.setOnClickListener {
            InputDialog.createAndShow(this)
        }
    }

    private fun Any.asMap() = this as Map<String, Any>
}