package com.example.str10

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class MainActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private var onlineMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Init Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        // Edge-to-edge display handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Prepínač Online / Offline režim
        val switchOnlineMode = findViewById<Switch>(R.id.switchButton)
        switchOnlineMode.setOnCheckedChangeListener { _, isChecked ->
            onlineMode = isChecked
        }

        // Udaje button
        val dataButton = findViewById<Button>(R.id.dataButton)
        dataButton.setOnClickListener {
            val angle = findViewById<EditText>(R.id.editAngleInput).text.toString().toDoubleOrNull() ?: 0.0
            val strength = findViewById<EditText>(R.id.editStrengthInput).text.toString().toDoubleOrNull() ?: 0.0

            if (onlineMode) {
                fetchDataFromServer(angle, strength) { result ->
                    openDataActivity(result.xCoords, result.yCoords, result.tCoords)
                }
            } else {
                val xCoords = mutableListOf<Double>()
                val yCoords = mutableListOf<Double>()
                val tCoords = mutableListOf<Double>()
                computeDataLocally(angle, strength, xCoords, yCoords, tCoords)
                openDataActivity(xCoords, yCoords, tCoords)
            }
        }

        // Graph button
        val graphButton = findViewById<Button>(R.id.graphButton)
        graphButton.setOnClickListener {
            val angle = findViewById<EditText>(R.id.editAngleInput).text.toString().toDoubleOrNull() ?: 0.0
            val strength = findViewById<EditText>(R.id.editStrengthInput).text.toString().toDoubleOrNull() ?: 0.0

            if (onlineMode) {
                fetchDataFromServer(angle, strength) { result ->
                    openGraphActivity(result.tCoords, result.yCoords)
                }
            } else {
                val xCoords = mutableListOf<Double>()
                val yCoords = mutableListOf<Double>()
                val tCoords = mutableListOf<Double>()
                computeDataLocally(angle, strength, xCoords, yCoords, tCoords)
                openGraphActivity(tCoords, yCoords)
            }
        }

        // Animation button
        val animationButton = findViewById<Button>(R.id.animationButton)
        animationButton.setOnClickListener {
            val angle = findViewById<EditText>(R.id.editAngleInput).text.toString().toDoubleOrNull() ?: 0.0
            val strength = findViewById<EditText>(R.id.editStrengthInput).text.toString().toDoubleOrNull() ?: 0.0

            if (onlineMode) {
                fetchDataFromServer(angle, strength) { result ->
                    openAnimationActivity(result.xCoords, result.yCoords)
                }
            } else {
                val xCoords = mutableListOf<Double>()
                val yCoords = mutableListOf<Double>()
                val tCoords = mutableListOf<Double>()
                computeDataLocally(angle, strength, xCoords, yCoords, tCoords)
                openAnimationActivity(xCoords, yCoords)
            }
        }
    }

    private fun computeDataLocally(
        angle: Double,
        speed: Double,
        xCoords: MutableList<Double>,
        yCoords: MutableList<Double>,
        tCoords: MutableList<Double>
    ) {
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

    private fun fetchDataFromServer(angle: Double, strength: Double, onSuccess: (ComputationResult) -> Unit) {
        val inputData = InputData(angle, strength)
        val call = apiService.compute(inputData)

        call.enqueue(object : Callback<ComputationResult> {
            override fun onResponse(call: Call<ComputationResult>, response: Response<ComputationResult>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        onSuccess(result)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Chyba servera", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ComputationResult>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Chyba siete: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun openDataActivity(xCoords: List<Double>, yCoords: List<Double>, tCoords: List<Double>) {
        val intent = Intent(this, DataActivity::class.java)
        intent.putExtra("xCoords", ArrayList(xCoords))
        intent.putExtra("yCoords", ArrayList(yCoords))
        intent.putExtra("tCoords", ArrayList(tCoords))
        startActivity(intent)
    }

    private fun openGraphActivity(tCoords: List<Double>, yCoords: List<Double>) {
        val intent = Intent(this, GraphActivity::class.java)
        intent.putExtra("tCoords", ArrayList(tCoords))
        intent.putExtra("yCoords", ArrayList(yCoords))
        startActivity(intent)
    }

    private fun openAnimationActivity(xCoords: List<Double>, yCoords: List<Double>) {
        val intent = Intent(this, AnimationActivity::class.java)
        intent.putExtra("xCoords", ArrayList(xCoords))
        intent.putExtra("yCoords", ArrayList(yCoords))
        startActivity(intent)
    }
}
