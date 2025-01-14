package com.posetrackerdemo

//import CreateRoutineFragment
//import CreateRoutineFragment
import CreateRoutineFragment
import PastRoutinesFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.posetrackerdemo.ui.home.HomeFragment
import com.posetrackerdemo.ui.my.MyFragment
//import com.posetrackerdemo.ui.tab2.CreateRoutineFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        // 기본 Fragment 설정
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, HomeFragment())
            .commit()

        // 네비게이션 아이템 선택 이벤트 처리
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_routines -> PastRoutinesFragment()
                R.id.nav_create_routine -> CreateRoutineFragment()
                R.id.nav_tab3 -> MyFragment()
                else -> HomeFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
            true
        }
    }
}
