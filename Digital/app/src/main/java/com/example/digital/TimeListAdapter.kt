package com.example.digital

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TimeListAdapter(private val timeRecords: List<TimeRecord>) :
    RecyclerView.Adapter<TimeListAdapter.TimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_time, parent, false)
        return TimeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val currentItem = timeRecords[position]

        holder.textViewDate.text = currentItem.date
        holder.textViewEntry.text = "Entrada: ${currentItem.entryTime}"
        holder.textViewLunchOut.text = "Saída Almoço: ${currentItem.lunchOutTime}"
        holder.textViewLunchIn.text = "Entrada Almoço: ${currentItem.lunchInTime}"
        holder.textViewExit.text = "Saída: ${currentItem.exitTime}"
    }

    override fun getItemCount() = timeRecords.size

    class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewEntry: TextView = itemView.findViewById(R.id.textViewEntry)
        val textViewLunchOut: TextView = itemView.findViewById(R.id.textViewLunchOut)
        val textViewLunchIn: TextView = itemView.findViewById(R.id.textViewLunchIn)
        val textViewExit: TextView = itemView.findViewById(R.id.textViewExit)
    }
}
