package com.example.str10

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class InputData(val angle: Double, val speed: Double)

data class ComputationResult(
    val xCoords: List<Double>,
    val yCoords: List<Double>,
    val tCoords: List<Double>
)

interface ApiService {
    @POST("/compute")
    fun compute(@Body inputData: InputData): Call<ComputationResult>
}
