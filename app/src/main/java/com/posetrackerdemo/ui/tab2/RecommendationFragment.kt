import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.posetrackerdemo.R
import com.posetrackerdemo.ui.home.LungeFragment
import com.posetrackerdemo.ui.home.PlankFragment
import com.posetrackerdemo.ui.home.PushupFragment
import com.posetrackerdemo.ui.home.SquatFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class RecommendationFragment : Fragment() {

    private lateinit var routine: List<String> // 루틴 데이터를 저장

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recommendation, container, false)


        // UI 요소 가져오기
        val recyclerView = view.findViewById<RecyclerView>(R.id.routineRecyclerView)
        val startButton = view.findViewById<Button>(R.id.startButton)

        // 전달받은 데이터
        val selectedExercises = arguments?.getStringArrayList("selectedExercises") ?: arrayListOf()
        val difficulty = arguments?.getString("difficulty") ?: "중"

        if (selectedExercises.isEmpty()) {
            Toast.makeText(requireContext(), "운동 데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
            return view
        }

        // 운동 루틴 생성
        routine = generateRoutine(selectedExercises, difficulty)

        // RecyclerView 설정
        val adapter = RecommendationAdapter(routine)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // 시작 버튼 클릭 이벤트
        startButton.setOnClickListener {
            startRoutine(routine)
        }

        return view
    }


    private fun generateRoutine(exercises: List<String>, difficulty: String): List<String> {
        val routine = mutableListOf<String>()

        val (reps, duration) = when (difficulty) {
            "하" -> Pair(10, 15)  // 강도 하: 10회/15초
            "중" -> Pair(30, 30)  // 강도 중: 30회/30초
            "상" -> Pair(50, 60)  // 강도 상: 50회/60초
            else -> Pair(30, 30)  // 기본값: 강도 중
        }

        for (i in 0 until 4) { // 4세트 생성
            for (exercise in exercises) {
                routine.add(
                    when (exercise) {
                        "스쿼트" -> "스쿼트 ${reps}회"
                        "푸쉬업" -> "푸쉬업 ${reps}회"
                        "플랭크" -> "플랭크 ${duration}초"
                        "런지" -> "런지 ${reps}회"
                        else -> ""
                    }
                )
            }
        }

        return routine
    }



    private fun startRoutine(routine: List<String>) {
        if (routine.isEmpty()) return

        val fragmentManager = parentFragmentManager ?: return


        // Launch a coroutine on the Main thread for UI updates
        CoroutineScope(Dispatchers.Main).launch {
            for (step in routine) {
                val fragment = when {
                    step.contains("스쿼트") -> SquatFragment()
                    step.contains("푸쉬업") -> PushupFragment()
                    step.contains("플랭크") -> PlankFragment()
                    step.contains("런지") -> LungeFragment()
                    else -> null
                }

                fragment?.let {
                    fragmentManager.beginTransaction()
                        .setCustomAnimations(
                            android.R.anim.fade_in, // Enter animation
                            android.R.anim.fade_out // Exit animation
                        )
                        .replace(R.id.fragmentContainer, it)
                        .commit()

                    // Wait for 10 seconds before transitioning to the next fragment
                    delay(30000L) // 10 seconds
                }
            }

        }
    }
}
