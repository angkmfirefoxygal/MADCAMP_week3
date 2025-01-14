package com.posetrackerdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.posetrackerdemo.ui.tab2.Exercise

class RecommendationAdapter(private val exercises: List<Exercise>) :
    RecyclerView.Adapter<RecommendationAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.exerciseName.text = exercise.name
        holder.exerciseCount.text = "${exercise.count}회"
        holder.exerciseImage.setImageResource(exercise.imageResId)
    }

    override fun getItemCount(): Int = exercises.size

    class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseName: TextView = view.findViewById(R.id.exerciseName)
        val exerciseCount: TextView = view.findViewById(R.id.exerciseCount)
        val exerciseImage: ImageView = view.findViewById(R.id.exerciseImage)
    }
}
