import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.posetrackerdemo.R

data class RoutineData(
    val date: String,
    val duration: String,
    val intensity: String,
    val tags: List<String>
)

class RoutinesAdapter(private val routines: List<RoutineData>) :
    RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_routine, parent, false)
        return RoutineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = routines[position]
        holder.routineDate.text = routine.date
        holder.routineDetails.text = "${routine.duration}, 운동 강도: ${routine.intensity}"
        holder.routineImage.setImageResource(R.drawable.ic_sample_image) // 이미지 설정

        // 태그 설정
        holder.routineTag1.text = routine.tags.getOrNull(0) ?: ""
        holder.routineTag1.visibility = if (routine.tags.size > 0) View.VISIBLE else View.GONE

        holder.routineTag2.text = routine.tags.getOrNull(1) ?: ""
        holder.routineTag2.visibility = if (routine.tags.size > 1) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int = routines.size

    class RoutineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val routineDate: TextView = view.findViewById(R.id.routineDate)
        val routineDetails: TextView = view.findViewById(R.id.routineDetails)
        val routineImage: ImageView = view.findViewById(R.id.routineImage)
        val routineTag1: TextView = view.findViewById(R.id.routineTag1)
        val routineTag2: TextView = view.findViewById(R.id.routineTag2)
    }
}
