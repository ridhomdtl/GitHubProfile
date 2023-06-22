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

class FollowingViewModel: ViewModel() {
    val followingList = MutableLiveData<ArrayList<UserInfo>>()

    fun setFollowingList(userlogin: String){
        ApiClient.apiInstance
            .getFollowing(userlogin)
            .enqueue(object : Callback<ArrayList<UserInfo>> {
                override fun onResponse(
                    call: Call<ArrayList<UserInfo>>,
                    response: Response<ArrayList<UserInfo>>
                ) {
                    if (response.isSuccessful){
                        followingList.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserInfo>>, t: Throwable) {
                    t.message?.let { Log.d( "Failure", it ) }
                }

            })
    }

    fun getFollowingList(): LiveData<ArrayList<UserInfo>> {
        return followingList
    }

}