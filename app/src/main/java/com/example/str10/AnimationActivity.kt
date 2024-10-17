package com.example.str10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        // Získanie súradníc z Intentu
        val xCoords = intent.getSerializableExtra("xCoords") as ArrayList<Double>
        val yCoords = intent.getSerializableExtra("yCoords") as ArrayList<Double>

        // Nastavenie animácie
        val animationView = findViewById<AnimationView>(R.id.anim_view)
        animationView.setCoords(xCoords, yCoords) // Nastav súradnice pre animáciu
    }
}
