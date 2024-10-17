package com.example.str10

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class GraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        // Prijmi odovzdané údaje z MainActivity
        val tCoords = intent.getSerializableExtra("tCoords") as? ArrayList<Double>
        val yCoords = intent.getSerializableExtra("yCoords") as? ArrayList<Double>

        // Pridaj logovanie na kontrolu prijatia dát
        Log.d("GraphActivity", "tCoords: $tCoords, yCoords: $yCoords")

        // Skontroluj, či nie sú údaje null alebo prázdne
        if (tCoords != null && yCoords != null && tCoords.isNotEmpty() && yCoords.isNotEmpty()) {
            // Nájdi GraphView
            val graphView: GraphView = findViewById(R.id.graph)

            // Vytvorenie dátových bodov pre graf
            val graphPoints = Array(tCoords.size) { i ->
                DataPoint(tCoords[i], yCoords[i])
            }

            // Vytvorenie série pre graf
            val series = LineGraphSeries(graphPoints)

            // Pridaj sériu do GraphView
            graphView.addSeries(series)

            // Nastavenie osí grafu
            graphView.gridLabelRenderer.horizontalAxisTitle = "Čas [s]"
            graphView.gridLabelRenderer.verticalAxisTitle = "Výška [m]"

            // Manuálne nastavenie rozsahov na osiach
            graphView.viewport.isXAxisBoundsManual = true
            graphView.viewport.setMinX(0.0)
            graphView.viewport.setMaxX(tCoords.maxOrNull() ?: 0.0 * 1.1)

            graphView.viewport.isYAxisBoundsManual = true
            graphView.viewport.setMinY(0.0)
            graphView.viewport.setMaxY(yCoords.maxOrNull()?.times(1.1) ?: 0.0)

            // Povolenie zoomovania a scrollovania
            graphView.viewport.isScalable = true
            graphView.viewport.isScrollable = true
        } else {
            // Log pre prípad, že sú údaje prázdne alebo null
            Log.e("GraphActivity", "Dáta sú neplatné alebo prázdne.")
        }
    }
}
