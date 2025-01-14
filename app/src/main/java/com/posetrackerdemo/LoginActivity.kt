package com.posetrackerdemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

import android.content.pm.PackageManager
import android.util.Base64
import java.security.MessageDigest



@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private val TAG = "KaKaoLogin"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // logoutUser(this)
        // 키 해시 얻기
        val DkeyHash = Utility.getKeyHash(this)
        Log.d("DKeyHash", "키 해시: ${KakaoSdk.keyHash}")


        // 카카오 로그인 버튼 클릭 이벤트
        val kakaoLoginButton = findViewById<Button>(R.id.btn_kakao_login)
        kakaoLoginButton.setOnClickListener {

            // 카카오톡으로 로그인 시도
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    startKakaoLogin(token, error)
                }
            } else {
                // 카카오계정으로 로그인 시도
                UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                    startKakaoLogin(token, error)
                }
            }

//            // HomeActivity로 이동
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish() // LoginActivity 종료
        }
    }



    private fun startKakaoLogin(token: OAuthToken?, error: Throwable?) {
        // 카카오 계정으로 로그인 콜백
        Log.d("DEBUG", "kakao login started")
        var loginSuccess = false

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오 로그인 실패", error)
                Toast.makeText(this, "카카오 로그인 실패: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, LoginActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                }, 4000) // 3초 대기
            } else if (token != null) {
                Log.i(TAG, "카카오 로그인 성공 ${token.accessToken}")
                Toast.makeText(this, "카카오 로그인 성공", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                }, 4000) // 3초 대기
            }
        }
        // 카카오톡이 설치되어 있는 경우
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            // 카카오톡이 설치되지 않은 경우 카카오 계정으로 로그인
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }


}
