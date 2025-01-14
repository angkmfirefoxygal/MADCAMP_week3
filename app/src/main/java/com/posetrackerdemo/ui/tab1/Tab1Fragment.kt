package com.posetrackerdemo.ui.tab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.posetrackerdemo.R

class Tab1Fragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment 레이아웃을 inflate
        return inflater.inflate(R.layout.fragment_tab1, container, false)
    }
}