package com.example.str10

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // listing
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dataButton = findViewById<Button>(R.id.dataButton)
        dataButton.setOnClickListener {
            val angle = findViewById<EditText>(R.id.editAngleInput).text.toString().toDoubleOrNull() ?: 0.0
            val strength = findViewById<EditText>(R.id.editStrengthInput).text.toString().toDoubleOrNull() ?: 0.0

            val xCoords = mutableListOf<Double>()
            val yCoords = mutableListOf<Double>()
            val tCoords = mutableListOf<Double>()

            computeDataLocally(angle, strength, xCoords, yCoords, tCoords)

            // Prechod na novú aktivitu s odovzdaním dát
            val intent = Intent(this, DataActivity::class.java)
            intent.putExtra("xCoords", ArrayList(xCoords))
            intent.putExtra("yCoords", ArrayList(yCoords))
            intent.putExtra("tCoords", ArrayList(tCoords))
            startActivity(intent)
        }

        // graph
        val graphButton = findViewById<Button>(R.id.graphButton)
        graphButton.setOnClickListener {
            val angle = findViewById<EditText>(R.id.editAngleInput).text.toString().toDoubleOrNull() ?: 0.0
            val strength = findViewById<EditText>(R.id.editStrengthInput).text.toString().toDoubleOrNull() ?: 0.0

            val xCoords = mutableListOf<Double>() // not used for graph
            val yCoords = mutableListOf<Double>()
            val tCoords = mutableListOf<Double>()
            computeDataLocally(angle, strength, xCoords, yCoords, tCoords)

            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("tCoords", ArrayList(tCoords)) // Časové súradnice
            intent.putExtra("yCoords", ArrayList(yCoords)) // Výškové súradnice
            startActivity(intent)
        }


        // animation
        val animationButton = findViewById<Button>(R.id.animationButton)
        animationButton.setOnClickListener {
            val angle = findViewById<EditText>(R.id.editAngleInput).text.toString().toDoubleOrNull() ?: 0.0
            val strength = findViewById<EditText>(R.id.editStrengthInput).text.toString().toDoubleOrNull() ?: 0.0

            val xCoords = mutableListOf<Double>()
            val yCoords = mutableListOf<Double>()
            val tCoords = mutableListOf<Double>()

            computeDataLocally(angle, strength, xCoords, yCoords, tCoords)

            // Prechod na AnimationActivity s dátami pre animáciu
            val intent = Intent(this, AnimationActivity::class.java)
            intent.putExtra("xCoords", ArrayList(xCoords))
            intent.putExtra("yCoords", ArrayList(yCoords))
            startActivity(intent)
        }
    }



    private fun computeDataLocally(angle: Double, speed: Double, xCoords: MutableList<Double>, yCoords: MutableList<Double>, tCoords: MutableList<Double>) {
        var x = 0.0
        var y = 0.0
        var t = 0.0
        val g = 9.81

        val timeStop = (2 * speed * sin(Math.toRadians(angle))) / g
        val timeInc = 0.1

        while (t < timeStop) {
            x = speed * t * cos(Math.toRadians(angle))
            y = speed * t * sin(Math.toRadians(angle)) - (g * t.pow(2)) / 2.0

            xCoords.add(x)
            yCoords.add(y)
            tCoords.add(t)

            t += timeInc
        }

        x = speed * timeStop * cos(Math.toRadians(angle))
        y = 0.0

        xCoords.add(x)
        yCoords.add(y)
        tCoords.add(timeStop)
    }



}

