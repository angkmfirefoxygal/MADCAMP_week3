package com.posetrackerdemo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.posetrackerdemo.ui.home.HomeFragment
import com.posetrackerdemo.ui.tab1.Tab1Fragment
import com.posetrackerdemo.ui.tab2.Tab2Fragment
import com.posetrackerdemo.ui.tab3.Tab3Fragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> Tab1Fragment()
            2 -> Tab2Fragment()
            3 -> Tab3Fragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}
