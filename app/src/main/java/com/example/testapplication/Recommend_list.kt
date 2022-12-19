package com.example.testapplication

import MyAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.databinding.ActivityRecommendListBinding
import com.example.testapplication.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Response


class Recommend_list : AppCompatActivity() {
    private val api = APIS.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecommendListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerViewItems: List<getRoomRecommendationModel.recommendationRoomInfo> = emptyList()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MyAdapter(recyclerViewItems)
        binding.recyclerView.adapter = adapter

        val preferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        api.getRoomRecommendation(
            preferences.getString("userId", ""), 24, 31, 32
        ).enqueue(object : retrofit2.Callback<getRoomRecommendationModel> {
            override fun onResponse(
                call: Call<getRoomRecommendationModel>,
                response: Response<getRoomRecommendationModel>
            ) {
                Log.d("정보", "${response.body()}")
                if (response.isSuccessful) {
                    response.body()?.let {
                        adapter.setRepoList(it.roomInfoList)
                    }
                }
            }

            override fun onFailure(call: Call<getRoomRecommendationModel>, t: Throwable) {
                Log.d("getRoomList", "fail")
            }

        })
    }
}