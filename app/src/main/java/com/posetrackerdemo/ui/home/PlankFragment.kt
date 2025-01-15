package com.posetrackerdemo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.posetrackerdemo.PlankTracker
import com.posetrackerdemo.PushupTracker
import com.posetrackerdemo.R


class PlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val intent = Intent(requireContext(), PlankTracker::class.java)
        startActivity(intent)
        return TODO("Provide the return value")
    }
}
