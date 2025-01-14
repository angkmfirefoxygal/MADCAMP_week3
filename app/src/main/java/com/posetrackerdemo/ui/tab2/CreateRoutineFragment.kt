import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.Slider
import com.posetrackerdemo.R

class CreateRoutineFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_routine, container, false)

        // UI 요소 초기화
        val chipGroup = view.findViewById<ChipGroup>(R.id.chipGroupExercises)
        val difficultySlider = view.findViewById<Slider>(R.id.difficultySlider)
        val createRoutineButton = view.findViewById<Button>(R.id.createRoutineButton)

        // Log: UI 요소가 초기화되었는지 확인
        Log.d("CreateRoutineFragment", "chipGroup initialized: $chipGroup")
        Log.d("CreateRoutineFragment", "difficultySlider initialized: $difficultySlider")
        Log.d("CreateRoutineFragment", "createRoutineButton initialized: $createRoutineButton")

        createRoutineButton.setOnClickListener {
            Log.d("CreateRoutineFragment", "Create Routine Button Clicked")

            val selectedExercises = mutableListOf<String>()
            for (i in 0 until chipGroup.childCount) {
                val chip = chipGroup.getChildAt(i) as Chip
                if (chip.isChecked) {
                    selectedExercises.add(chip.text.toString())
                }
            }

            // Log: 선택된 운동 확인
            Log.d("CreateRoutineFragment", "Selected Exercises: $selectedExercises")

            val difficulty = when (difficultySlider.value.toInt()) {
                0 -> "하"
                1 -> "중"
                2 -> "상"
                else -> "중"
            }

            // Log: 선택된 강도 확인
            Log.d("CreateRoutineFragment", "Selected Difficulty: $difficulty")

            if (selectedExercises.isEmpty()) {
                Toast.makeText(requireContext(), "운동을 선택해주세요!", Toast.LENGTH_SHORT).show()
                Log.d("CreateRoutineFragment", "No exercises selected, showing Toast")
            } else {
                // 데이터 전달
                val bundle = Bundle()
                bundle.putStringArrayList("selectedExercises", ArrayList(selectedExercises))
                bundle.putString("difficulty", difficulty)

                val recommendationFragment = RecommendationFragment()
                recommendationFragment.arguments = bundle

                // Log: Fragment 전환 준비
                Log.d("CreateRoutineFragment", "Switching to RecommendationFragment with data")

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, recommendationFragment)
                    .addToBackStack(null)
                    .commit()

                // Log: Fragment 전환 완료
                Log.d("CreateRoutineFragment", "Fragment switched successfully")
            }
        }

        return view
    }
}