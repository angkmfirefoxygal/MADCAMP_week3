package com.posetrackerdemo.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.posetrackerdemo.LungeTracker
import com.posetrackerdemo.PlankTracker
import com.posetrackerdemo.PushupTracker
import com.posetrackerdemo.R
import com.posetrackerdemo.SquatTracker


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)


        // Push-Up Button 클릭 시 PushupFragment로 이동
        val pushupButton = view.findViewById<LinearLayout>(R.id.pushup_button)
        pushupButton.setOnClickListener {
            val intent = Intent(requireContext(), PushupTracker::class.java)
            startActivity(intent)
        }

        // Squat Button 클릭 시 SquatFragment로 이동
        val squatButton = view.findViewById<LinearLayout>(R.id.squat_button)
        squatButton.setOnClickListener {
            val intent = Intent(requireContext(), SquatTracker::class.java)
            startActivity(intent)
        }

        // Lunge Button 클릭 시 LungeFragment로 이동
        val lungeButton = view.findViewById<LinearLayout>(R.id.lunge_button)
        lungeButton.setOnClickListener {
            val intent = Intent(requireContext(), LungeTracker::class.java)
            startActivity(intent)
        }

        // Plank Button 클릭 시 PlankFragment로 이동
        val plankButton = view.findViewById<LinearLayout>(R.id.plank_button)
        plankButton.setOnClickListener {
            val intent = Intent(requireContext(), PlankTracker::class.java)
            startActivity(intent)
        }

        return view

    }





}