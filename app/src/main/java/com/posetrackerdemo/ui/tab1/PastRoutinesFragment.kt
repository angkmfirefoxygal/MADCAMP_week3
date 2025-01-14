import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.posetrackerdemo.R

class PastRoutinesFragment : Fragment() {

    private val routines = listOf(
        RoutineData("2024/12/31", "30분", "상", listOf("스쿼트", "플랭크")),
        RoutineData("2024/12/30", "25분", "중", listOf("런지", "푸쉬업")),
        RoutineData("2024/12/29", "20분", "하", listOf("플랭크")),
        RoutineData("2024/12/28", "30분", "상", listOf("스쿼트", "런지")),
        RoutineData("2024/12/27", "15분", "하", listOf("플랭크", "스쿼트"))
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_past_routines, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RoutinesAdapter(routines)
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        return view
    }
}
