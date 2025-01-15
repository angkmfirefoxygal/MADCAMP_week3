package com.posetrackerdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class RoutineData(val date: String, val duration: String, val intensity: String, val tags: List<String>,val imageResId:Int)

class RoutinesAdapter(private val routines: List<RoutineData>) :
    RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_routine, parent, false)
        return RoutineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = routines[position]
        holder.date.text = routine.date
        holder.details.text = "${routine.duration}, 운동 강도: ${routine.intensity}"
        //holder.tags.text = routine.tags.joinToString(", ")
    }

    override fun getItemCount(): Int = routines.size

    class RoutineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.routineDate)
        val details: TextView = view.findViewById(R.id.routineDetails)
        //val tags: TextView = view.findViewById(R.id.routineTags)
    }
}
