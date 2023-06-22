package com.example.githubprofile.detailactivity.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubprofile.apiservice.ApiClient
import com.example.githubprofile.usermodeldatabase.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {
    val followersList = MutableLiveData<ArrayList<UserInfo>>()

    fun setFollowersList(userlogin: String){
        ApiClient.apiInstance
            .getFollowers(userlogin)
            .enqueue(object : Callback<ArrayList<UserInfo>>{
                override fun onResponse(
                    call: Call<ArrayList<UserInfo>>,
                    response: Response<ArrayList<UserInfo>>
                ) {
                    if (response.isSuccessful){
                        followersList.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserInfo>>, t: Throwable) {
                    t.message?.let { Log.d( "Failure", it ) }
                }

            })
    }

    fun getFollowersList(): LiveData<ArrayList<UserInfo>> {
        return followersList
    }

}