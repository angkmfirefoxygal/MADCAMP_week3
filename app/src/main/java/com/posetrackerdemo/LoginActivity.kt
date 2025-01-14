package com.posetrackerdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.posetrackerdemo.ui.home.HomeFragment


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 카카오 로그인 버튼 클릭 이벤트
        val kakaoLoginButton = findViewById<Button>(R.id.btn_kakao_login)
        kakaoLoginButton.setOnClickListener {
            // HomeActivity로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // LoginActivity 종료
        }
    }
}
