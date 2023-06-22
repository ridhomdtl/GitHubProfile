package com.example.githubprofile.mainactivity

import android.util.Log
import androidx.lifecycle.*
import com.example.githubprofile.apiservice.ApiClient
import com.example.githubprofile.appbar.setting.SettingPreferences
import com.example.githubprofile.usermodeldatabase.UserDataResponse
import com.example.githubprofile.usermodeldatabase.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val preferences: SettingPreferences) : ViewModel() {

    val userList = MutableLiveData<ArrayList<UserInfo>>()

    fun getThemeSettings(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    fun setSearchUsers(query: String){
        ApiClient.apiInstance
            .getSearch(query)
            .enqueue(object : Callback<UserDataResponse>{
                override fun onResponse(
                    call: Call<UserDataResponse>,
                    response: Response<UserDataResponse>
                ) {
                    if(response.isSuccessful){
                        userList.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                    t.message?.let { Log.d( "Failure", it ) }
                }
            })
    }

    fun getSearchUser(): LiveData<ArrayList<UserInfo>>{
        return userList
    }
}