package com.posetrackerdemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.posetrackerdemo.R

import android.widget.ImageView
import android.widget.TextView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class HomeFragment : Fragment() {

    private lateinit var tvGlucoseValue: TextView
    private lateinit var tvTrend: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvSystemTime: TextView
    private lateinit var ivTrendChart: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // XML 요소와 연결
        tvGlucoseValue = view.findViewById(R.id.tvGlucoseValue)
        tvTrend = view.findViewById(R.id.tvTrend)
        tvStatus = view.findViewById(R.id.tvStatus)
        tvSystemTime = view.findViewById(R.id.tvSystemTime)
        ivTrendChart = view.findViewById(R.id.ivTrendChart)

        // API 데이터 가져오기
        fetchEgvData()

        return view

    }

    private fun fetchEgvData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.dexcom.com/v3/users/self/egvs?startDate=2022-02-06T09:12:35&endDate=2022-02-06T09:12:35")
            .addHeader("Bearer", "t6n9l8Y33IF81uvy")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val json = JSONObject(responseBody ?: "")

                    val records = json.getJSONArray("records")
                    if (records.length() > 0) {
                        val firstRecord = records.getJSONObject(0)

                        val value = firstRecord.getInt("value")
                        val trend = firstRecord.getString("trend")
                        val status = firstRecord.optString("status", "ok")
                        val systemTime = firstRecord.getString("systemTime")

                        activity?.runOnUiThread {
                            tvGlucoseValue.text = "Glucose Value: $value mg/dL"
                            tvTrend.text = "Trend: $trend"
                            tvStatus.text = "Status: $status"
                            tvSystemTime.text = "System Time: $systemTime"
                            // Trend Chart 처리 (추후 구현)
                        }
                    }
                }
            }
        })
    }
}