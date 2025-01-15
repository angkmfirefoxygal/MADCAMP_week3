import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.posetrackerdemo.R

class RecommendationAdapter(private val routine: List<String>) :
    RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseIcon: ImageView = view.findViewById(R.id.exerciseIcon)
        val exerciseText: TextView = view.findViewById(R.id.exerciseText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recommendation_routine_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = routine[position]

        // 텍스트 설정
        holder.exerciseText.text = exercise

        // 아이콘 설정
        when {
            exercise.contains("스쿼트") -> holder.exerciseIcon.setImageResource(R.drawable.squat)
            exercise.contains("푸쉬업") -> holder.exerciseIcon.setImageResource(R.drawable.pushup)
            exercise.contains("플랭크") -> holder.exerciseIcon.setImageResource(R.drawable.plank)
            exercise.contains("런지") -> holder.exerciseIcon.setImageResource(R.drawable.lunge)
            else -> holder.exerciseIcon.setImageResource(R.drawable.ic_exercise)
        }
    }

    override fun getItemCount(): Int = routine.size
}
