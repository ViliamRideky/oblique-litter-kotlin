package com.example.str10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(
    private val xCoords: List<Double>,
    private val yCoords: List<Double>,
    private val tCoords: List<Double>
) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val xTextView: TextView = itemView.findViewById(R.id.xTextView)
        val yTextView: TextView = itemView.findViewById(R.id.yTextView)
        val tTextView: TextView = itemView.findViewById(R.id.tTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.xTextView.text = String.format("%.3f", xCoords[position])
        holder.yTextView.text = String.format("%.3f", yCoords[position])
        holder.tTextView.text = String.format("%.3f", tCoords[position])
    }

    override fun getItemCount(): Int {
        return xCoords.size
    }
}
