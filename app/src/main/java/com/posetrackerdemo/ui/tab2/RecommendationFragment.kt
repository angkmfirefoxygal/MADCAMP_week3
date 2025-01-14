import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.posetrackerdemo.R
import com.posetrackerdemo.RecommendationAdapter
import com.posetrackerdemo.SquatTracker
import com.posetrackerdemo.ui.tab2.Exercise

class RecommendationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recommendation, container, false)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // Set to 2 columns
        recyclerView.adapter = RecommendationAdapter(getRecommendations()) // Add your adapter and data

        // Handle "운동 시작하기" button
        val startExerciseButton: MaterialButton = view.findViewById(R.id.startExerciseButton)
        startExerciseButton.setOnClickListener {
            // Navigate to SquatTracker Activity
            val intent = Intent(requireContext(), SquatTracker::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun getRecommendations(): List<Exercise> {
        return listOf(
            Exercise("푸쉬업", 20, R.drawable.pushup),
            Exercise("스쿼트", 30, R.drawable.squat),
            Exercise("런지", 15, R.drawable.lunge),
            Exercise("플랭크", 40, R.drawable.plank)
        )
    }
}
