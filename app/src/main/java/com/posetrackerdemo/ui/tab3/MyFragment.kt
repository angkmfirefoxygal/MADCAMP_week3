package com.posetrackerdemo.ui.my

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.posetrackerdemo.R
import com.bumptech.glide.Glide

class MyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        val view = inflater.inflate(R.layout.fragment_my, container, false)

        val profileImage = view.findViewById<ImageView>(R.id.profileImage) // view를 통해 호출
        val nickname = view.findViewById<TextView>(R.id.username)
        val email = view.findViewById<TextView>(R.id.userEmail)

        // SharedPreferences에서 사용자 정보 가져오기
        val sharedPreferences = requireContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)

        val userNickname = sharedPreferences.getString("nickname", "닉네임 없음")
        val userEmail = sharedPreferences.getString("email", "이메일 없음")
        val userProfileImage = sharedPreferences.getString("profileImage", null)
        //val userId = sharedPreferences.getString("memberId", "회원번호 없음")

        // 사용자 정보 매핑
        nickname.text = userNickname
        email.text = userEmail

        // 프로필 이미지 로드 (Glide 사용)
        if (!userProfileImage.isNullOrEmpty()) {
            Glide.with(this)
                .load(userProfileImage)
                .circleCrop()
                .into(profileImage)
        }

        return inflater.inflate(R.layout.fragment_my, container, false)

    }
}
