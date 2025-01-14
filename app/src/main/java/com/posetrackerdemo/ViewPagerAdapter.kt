package com.posetrackerdemo

import CreateRoutineFragment
import PastRoutinesFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.posetrackerdemo.ui.home.HomeFragment
import com.posetrackerdemo.ui.my.MyFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> PastRoutinesFragment()
            2 -> CreateRoutineFragment()
            3 -> MyFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}
