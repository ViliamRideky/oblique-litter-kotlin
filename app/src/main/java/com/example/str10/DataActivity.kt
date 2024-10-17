package com.example.str10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Prijmi odovzdané údaje
        val xCoords = intent.getSerializableExtra("xCoords") as ArrayList<Double>
        val yCoords = intent.getSerializableExtra("yCoords") as ArrayList<Double>
        val tCoords = intent.getSerializableExtra("tCoords") as ArrayList<Double>

        // Nastav RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DataAdapter(xCoords, yCoords, tCoords)
    }
}