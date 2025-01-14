import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.Slider
import com.posetrackerdemo.R

class CreateRoutineFragment : Fragment() {

    private lateinit var viewModel: CreateRoutineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_routine, container, false)
        viewModel = ViewModelProvider(this).get(CreateRoutineViewModel::class.java)

        val chipGroup: ChipGroup = view.findViewById(R.id.chipGroupExercises)
        val selectedExercisesText: TextView = view.findViewById(R.id.selectedExercisesText)
        val selectedDifficultyText: TextView = view.findViewById(R.id.selectedDifficultyText)
        val createRoutineButton: View = view.findViewById(R.id.createRoutineButton)
        val difficultySlider: Slider = view.findViewById(R.id.difficultySlider)

        // Update chips selection
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedExercises = mutableListOf<String>()
            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i) as Chip
                if (chip.isChecked) {
                    selectedExercises.add(chip.text.toString())
                }
            }
            viewModel.setSelectedExercises(selectedExercises)
        }

        // Observe changes in exercise selection
        viewModel.selectedExercises.observe(viewLifecycleOwner) { exercises ->
            selectedExercisesText.text = "선택한 운동: ${exercises.joinToString(", ")}"
        }

        // Difficulty slider change listener
        difficultySlider.addOnChangeListener { _, value, _ ->
            val difficulty = when (value.toInt()) {
                0 -> "하"
                1 -> "중"
                2 -> "상"
                else -> "중"
            }
            viewModel.setSelectedDifficulty(difficulty)
        }

        // Observe difficulty level changes
        viewModel.selectedDifficulty.observe(viewLifecycleOwner) { difficulty ->
            selectedDifficultyText.text = "선택한 강도: $difficulty"
        }

        // Handle routine creation
        createRoutineButton.setOnClickListener {
            val fragment = RecommendationFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment) // Ensure R.id.fragmentContainer matches your container ID
                .addToBackStack(null) // This ensures you can go back to the CreateRoutineFragment
                .commit()
        }


        return view
    }
}

class CreateRoutineViewModel : ViewModel() {
    val selectedExercises = MutableLiveData<List<String>>()
    val selectedDifficulty = MutableLiveData<String>()

    fun setSelectedExercises(exercises: List<String>) {
        selectedExercises.value = exercises
    }

    fun setSelectedDifficulty(difficulty: String) {
        selectedDifficulty.value = difficulty
    }
}
